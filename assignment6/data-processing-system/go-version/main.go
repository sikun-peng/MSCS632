package main

import (
	"fmt"
	"log"
	"os"
	"strings"
	"sync"
	"time"
)

type Task struct {
	ID   int
	Data string
}

type TaskQueue struct {
	tasks chan Task
}

func NewTaskQueue(buffer int) *TaskQueue {
	return &TaskQueue{tasks: make(chan Task, buffer)}
}

func (q *TaskQueue) AddTask(task Task) {
	q.tasks <- task
}

func (q *TaskQueue) Close() {
	close(q.tasks)
}

type ResultStore struct {
	mu      sync.Mutex
	results []string
}

func (s *ResultStore) AddResult(result string) {
	s.mu.Lock()
	defer s.mu.Unlock()
	s.results = append(s.results, result)
}

func (s *ResultStore) Snapshot() []string {
	s.mu.Lock()
	defer s.mu.Unlock()

	snapshot := make([]string, len(s.results))
	copy(snapshot, s.results)
	return snapshot
}

func (s *ResultStore) WriteToFile(fileName string) error {
	file, err := os.Create(fileName)
	if err != nil {
		return err
	}
	defer file.Close()

	for _, result := range s.Snapshot() {
		if _, err := fmt.Fprintln(file, result); err != nil {
			return err
		}
	}

	return nil
}

func processTask(workerID int, task Task) (string, error) {
	log.Printf("Worker-%d processing Task-%d", workerID, task.ID)

	if strings.TrimSpace(task.Data) == "" {
		return "", fmt.Errorf("task %d has empty data", task.ID)
	}

	time.Sleep(time.Duration(300+task.ID*50) * time.Millisecond)
	processed := strings.ToUpper(task.Data)
	return fmt.Sprintf("Task-%d processed by Worker-%d: %s", task.ID, workerID, processed), nil
}

func worker(workerID int, queue *TaskQueue, store *ResultStore, wg *sync.WaitGroup) {
	defer wg.Done()
	log.Printf("Worker-%d started", workerID)

	for task := range queue.tasks {
		result, err := processTask(workerID, task)
		if err != nil {
			log.Printf("Worker-%d encountered an error: %v", workerID, err)
			continue
		}

		store.AddResult(result)
		log.Printf("Worker-%d saved result for Task-%d", workerID, task.ID)
	}

	log.Printf("Worker-%d completed all tasks", workerID)
}

func main() {
	const workerCount = 3

	queue := NewTaskQueue(8)
	store := &ResultStore{}
	var wg sync.WaitGroup

	for i := 1; i <= workerCount; i++ {
		wg.Add(1)
		go worker(i, queue, store, &wg)
	}

	for i := 1; i <= 8; i++ {
		queue.AddTask(Task{
			ID:   i,
			Data: fmt.Sprintf("dataset-%d", i),
		})
	}
	queue.Close()

	wg.Wait()

	if err := store.WriteToFile("go_results.txt"); err != nil {
		log.Printf("Failed to write Go results file: %v", err)
		return
	}

	log.Printf("Results written to go_results.txt")
	log.Printf("Total results collected: %d", len(store.Snapshot()))
}
