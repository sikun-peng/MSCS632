# Employee Schedule Manager

This project implements an employee weekly schedule manager in two languages: Python and Java. It demonstrates control structures such as conditionals, loops, branching, conflict detection, and random assignment while building a 7-day shift schedule.

## Assignment Description

The company operates every day of the week with three daily shifts:

- Morning
- Afternoon
- Evening

The scheduler must:

- keep each employee to at most one shift per day
- keep each employee to at most five days per week
- staff each shift with at least two employees when possible
- limit each shift to a maximum of three employees
- resolve full-shift conflicts by trying another shift on the same day
- try the next day if no shift is available on the current day
- print the final weekly schedule in a readable format

## Languages Used

- Python
- Java

## Project Structure

```text
employee-schedule-manager/
├── README.md
├── java-version/
│   ├── Day.java
│   ├── Employee.java
│   ├── Main.java
│   └── Shift.java
├── python-version/
│   └── employee_scheduler.py
└── screenshots/
```

## How to Run the Python Version

From the project root:

```bash
cd python-version
python employee_scheduler.py
```

## How to Run the Java Version

From the project root:

```bash
cd java-version
javac Main.java Employee.java Day.java Shift.java
java Main
```

## Control Structures Used

Both versions intentionally demonstrate:

- `if / elif / else` or `if / else` branching
- `for` loops for day, shift, and employee traversal
- `while` loops for filling understaffed shifts
- `continue` to skip ineligible employees
- `break` after a successful reassignment
- nested data structures for the weekly schedule
- random assignment when a shift needs additional staff

## Scheduling Approach

1. Create a schedule entry for every day and shift.
2. Loop through each day and each employee.
3. Try the employee's preferred shift first.
4. If the preferred shift is full, print a conflict message and try the other same-day shifts.
5. If the day is full, try the next day.
6. After preference-based scheduling, fill any shift with fewer than two employees by randomly assigning eligible employees.
7. Validate all assignment rules and print warnings if a rule cannot be met.

## Screenshot Section

Add your Python and Java output screenshots in the `screenshots/` directory before submission:

- `screenshots/python-output.png`
- `screenshots/java-output.png`
