import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SharedTaskQueue {
    private final Queue<Task> tasks = new LinkedList<>();
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private boolean acceptingTasks = true;

    public void addTask(Task task) {
        lock.lock();
        try {
            if (!acceptingTasks) {
                throw new IllegalStateException("Queue is closed for new tasks.");
            }
            tasks.offer(task);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Task getTask() throws InterruptedException {
        lock.lock();
        try {
            while (tasks.isEmpty() && acceptingTasks) {
                notEmpty.await();
            }

            if (tasks.isEmpty() && !acceptingTasks) {
                return null;
            }

            return tasks.poll();
        } finally {
            lock.unlock();
        }
    }

    public void close() {
        lock.lock();
        try {
            acceptingTasks = false;
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
