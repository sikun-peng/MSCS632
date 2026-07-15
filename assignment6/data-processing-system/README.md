# Multi-threaded Data Processing System

This project implements Assignment 6 in two languages:

- Java
- Go

Both versions simulate multiple workers processing tasks from a shared queue in parallel, storing results safely, logging activity, and writing final output to a shared file.

## Project Structure

```text
data-processing-system/
├── README.md
├── go-version/
│   ├── main.go
│   └── sample_output.txt
├── java-version/
│   ├── Main.java
│   ├── ResultStore.java
│   ├── SharedTaskQueue.java
│   ├── Task.java
│   ├── Worker.java
│   └── sample_output.txt
└── report/
    └── assignment6_report.md
```

## Features

- Shared queue with synchronized access
- Multiple worker threads or goroutines
- Simulated processing delay
- Shared result storage with safe concurrent writes
- Logging for task execution, worker lifecycle, and errors
- Output file generation after processing completes

## How to Run the Java Version

From the `java-version` directory:

```bash
javac Main.java Task.java SharedTaskQueue.java ResultStore.java Worker.java
java Main
```

Expected artifacts:

- console logs showing workers starting, processing tasks, and finishing
- `java_results.txt`

## How to Run the Go Version

From the `go-version` directory:

```bash
go run main.go
```

Expected artifacts:

- console logs showing goroutines starting, processing tasks, and finishing
- `go_results.txt`

## Submission Notes

- Replace the report placeholder GitHub URL with your real repository link.
- Append screenshots of code and sample output for both language versions.
- Format the final report according to APA 7 guidelines before exporting to PDF or DOCX.
