# Multi-Paradigm Problem Solving

This project implements Assignment 7 in three languages and paradigms:

- C: procedural
- OCaml: functional
- Python: object-oriented

Each version calculates the mean, median, and mode for a list of integers.

## Project Structure

```text
multi-paradigm-statistics/
├── README.md
├── c-version/
│   ├── statistics.c
│   └── sample_output.txt
├── ocaml-version/
│   ├── statistics.ml
│   └── sample_output.txt
└── python-version/
    ├── statistics_calculator.py
    └── sample_output.txt
```

## How to Run

### C

```bash
cd c-version
clang -std=c11 -Wall -Wextra -pedantic statistics.c -o statistics
./statistics
```

### OCaml

```bash
cd ocaml-version
ocaml statistics.ml
```

### Python

```bash
cd python-version
python3 statistics_calculator.py
```

## Submission Notes

- Replace the report placeholder GitHub URL with your real repository link.
- Append screenshots of code and sample output for all three languages.
- Format the final report in APA 7 style before exporting to PDF or DOCX.
