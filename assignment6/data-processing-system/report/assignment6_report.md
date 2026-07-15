# Assignment 6 Report: Multi-threaded Data Processing System

**Student:** Your Name  
**Course:** MSCS-632-M30 Advanced Programming Languages  
**Term:** Summer 2026  
**GitHub Repository:** `https://github.com/your-username/your-assignment6-repo`

## Overview

This assignment implements a multi-threaded Data Processing System in Java and Go. In both versions, multiple workers retrieve tasks from a shared queue, simulate processing work in parallel, store results in a shared resource, and log execution details. The goal is to demonstrate safe concurrency, orderly worker termination, and proper exception or error handling in two languages with different concurrency models.

## Java Concurrency and Exception Handling

The Java implementation uses a shared queue class protected by a `ReentrantLock` and `Condition`. This design ensures that only one thread can modify the queue at a time and that workers wait safely when the queue is empty instead of busy-waiting. Worker threads are managed with an `ExecutorService`, which simplifies thread creation and shutdown. Each worker repeatedly retrieves a task from the queue, simulates processing by sleeping for a short time, and saves the processed result to a shared `ResultStore`. The result store uses a synchronized list so that multiple threads can write results without corrupting shared state.

Java handles failures primarily through exceptions, so the implementation uses `try-catch` blocks in both the worker and main execution paths. Workers catch `InterruptedException` to support safe shutdown and restore the thread’s interrupted status when necessary. The main method catches `IOException` when writing results to a file and logs the error instead of allowing the program to fail silently. Additional validation checks, such as rejecting empty task data, prevent invalid processing and make the system more robust.

## Go Concurrency and Error Handling

The Go implementation uses goroutines and channels, which provide a simpler built-in model for concurrent task processing. A buffered channel acts as the shared task queue, allowing multiple goroutines to receive tasks safely without explicit lock management for queue access. A `WaitGroup` ensures that the main function waits until all workers complete before the program exits. Results are stored in a shared structure protected by a `sync.Mutex`, which prevents race conditions when multiple goroutines append processed data.

Go does not rely on exceptions in the same way as Java. Instead, functions return errors explicitly, and the caller checks each error immediately. In this implementation, task processing returns an error if task data is invalid, and file creation or file writing errors are handled directly in the main flow. The program also uses `defer` to ensure files are closed properly after writing. This approach makes error handling explicit and predictable, while still supporting graceful termination of all workers.

## Comparison

Java and Go both support safe parallel processing, but they approach concurrency differently. Java uses thread pools, locks, and exception handling to coordinate work, which offers detailed control but requires more explicit synchronization code. Go emphasizes lightweight goroutines, channels, and explicit error returns, which can make concurrent programs shorter and easier to reason about. In both implementations, the shared queue is protected, results are written safely, worker activity is logged, and all processing ends cleanly without deadlock.

## APA 7 Submission Notes

- Add a title page if your instructor requires one.
- Use double spacing, 12-point Times New Roman, and 1-inch margins in the final document.
- Replace the GitHub repository placeholder with the real repository URL.
- Append screenshots of both code and sample output for Java and Go after the discussion section.
