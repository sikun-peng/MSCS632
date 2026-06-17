import random
from dataclasses import dataclass, field


DAYS = [
    "Monday",
    "Tuesday",
    "Wednesday",
    "Thursday",
    "Friday",
    "Saturday",
    "Sunday",
]

SHIFTS = ["Morning", "Afternoon", "Evening"]
MIN_EMPLOYEES_PER_SHIFT = 2
MAX_EMPLOYEES_PER_SHIFT = 3
MAX_DAYS_PER_EMPLOYEE = 5
MIN_TOTAL_ASSIGNMENTS_PER_DAY = MIN_EMPLOYEES_PER_SHIFT * len(SHIFTS)
RANDOM_SEED = 42


@dataclass
class Employee:
    name: str
    preferences: dict
    assigned_days: set = field(default_factory=set)
    assignments: dict = field(default_factory=dict)

    @property
    def assigned_day_count(self) -> int:
        return len(self.assigned_days)

    def reassign(self, day, shift):
        self.assignments[day] = shift


def build_employees():
    names = [
        "Alice",
        "Bob",
        "Carlos",
        "Diana",
        "Ethan",
        "Fiona",
        "George",
        "Hannah",
        "Ivan",
        "Julia",
    ]
    base_preferences = [
        "Morning",
        "Morning",
        "Morning",
        "Afternoon",
        "Afternoon",
        "Evening",
        "Evening",
        "Morning",
        "Afternoon",
        "Evening",
    ]

    employees = []
    for index, name in enumerate(names):
        preferences = {}
        for day_index, day in enumerate(DAYS):
            if (index + day_index) % 4 == 0:
                preference = "Morning"
            elif day_index % 3 == 2:
                preference = SHIFTS[(index + 1) % len(SHIFTS)]
            else:
                preference = base_preferences[index]
            preferences[day] = preference
        employees.append(Employee(name=name, preferences=preferences))
    return employees


def initialize_schedule():
    return {day: {shift: [] for shift in SHIFTS} for day in DAYS}


def day_assignment_count(schedule, day):
    return sum(len(schedule[day][shift]) for shift in SHIFTS)


def can_assign(employee, schedule, day, shift):
    if day in employee.assigned_days:
        return False
    if employee.assigned_day_count >= MAX_DAYS_PER_EMPLOYEE:
        return False
    if len(schedule[day][shift]) >= MAX_EMPLOYEES_PER_SHIFT:
        return False
    return True


def assign_employee(employee, schedule, day, shift):
    if not can_assign(employee, schedule, day, shift):
        return False
    schedule[day][shift].append(employee.name)
    employee.assigned_days.add(day)
    employee.assignments[day] = shift
    return True


def rebalance_day(schedule, employees_by_name, day):
    changed = True
    while changed:
        changed = False
        for shift in SHIFTS:
            if len(schedule[day][shift]) >= MIN_EMPLOYEES_PER_SHIFT:
                continue

            donor_shift = None
            for candidate_shift in SHIFTS:
                if len(schedule[day][candidate_shift]) > MIN_EMPLOYEES_PER_SHIFT:
                    donor_shift = candidate_shift
                    break

            if donor_shift is None:
                continue

            donor_names = schedule[day][donor_shift]
            selected_name = None
            for name in donor_names:
                if employees_by_name[name].preferences[day] == shift:
                    selected_name = name
                    break

            if selected_name is None:
                selected_name = donor_names[-1]

            schedule[day][donor_shift].remove(selected_name)
            schedule[day][shift].append(selected_name)
            employees_by_name[selected_name].reassign(day, shift)
            print(
                f"Rebalanced {selected_name} from {day} {donor_shift} to {day} {shift}."
            )
            changed = True
            break


def build_schedule(employees):
    schedule = initialize_schedule()
    random_generator = random.Random(RANDOM_SEED)
    employees_by_name = {employee.name: employee for employee in employees}

    for day_index, day in enumerate(DAYS):
        ordered_employees = sorted(
            employees, key=lambda employee: (employee.assigned_day_count, employee.name)
        )

        for employee in ordered_employees:
            if day_assignment_count(schedule, day) >= MIN_TOTAL_ASSIGNMENTS_PER_DAY:
                break
            if day in employee.assigned_days:
                continue
            if employee.assigned_day_count >= MAX_DAYS_PER_EMPLOYEE:
                continue

            preferred_shift = employee.preferences[day]
            if assign_employee(employee, schedule, day, preferred_shift):
                continue

            if len(schedule[day][preferred_shift]) >= MAX_EMPLOYEES_PER_SHIFT:
                print(
                    f"Conflict detected for {employee.name} on {day} {preferred_shift}."
                )

            assigned = False
            for shift in SHIFTS:
                if shift == preferred_shift:
                    continue
                if assign_employee(employee, schedule, day, shift):
                    print(
                        f"Resolved by assigning {employee.name} to {day} {shift}."
                    )
                    assigned = True
                    break

            if assigned:
                continue

            if day_index == len(DAYS) - 1:
                print(f"Warning: no shift available for {employee.name} after {day}.")
                continue

            next_day = DAYS[day_index + 1]
            if day_assignment_count(schedule, next_day) < MIN_TOTAL_ASSIGNMENTS_PER_DAY:
                for shift in SHIFTS:
                    if assign_employee(employee, schedule, next_day, shift):
                        print(
                            f"Moved {employee.name} to {next_day} {shift} after conflict on {day}."
                        )
                        assigned = True
                        break

            if not assigned:
                print(
                    f"Warning: unable to assign {employee.name} on {day} or {next_day}."
                )

    for day in DAYS:
        rebalance_day(schedule, employees_by_name, day)

    for day in DAYS:
        for shift in SHIFTS:
            while len(schedule[day][shift]) < MIN_EMPLOYEES_PER_SHIFT:
                eligible_employees = []
                for employee in employees:
                    if can_assign(employee, schedule, day, shift):
                        eligible_employees.append(employee)

                if not eligible_employees:
                    print(
                        f"Warning: could not fully staff {day} {shift}; no eligible employees remain."
                    )
                    break

                selected_employee = random_generator.choice(eligible_employees)
                assign_employee(selected_employee, schedule, day, shift)
                print(
                    f"Randomly assigned {selected_employee.name} to {day} {shift}."
                )

    return schedule


def validate_schedule(schedule, employees):
    print("\nValidation")
    print("----------")
    valid = True

    for employee in employees:
        if employee.assigned_day_count > MAX_DAYS_PER_EMPLOYEE:
            print(
                f"Warning: {employee.name} works {employee.assigned_day_count} days."
            )
            valid = False

    for day in DAYS:
        seen_names = set()
        for shift in SHIFTS:
            assigned_names = schedule[day][shift]
            if len(assigned_names) > MAX_EMPLOYEES_PER_SHIFT:
                print(f"Warning: {day} {shift} exceeds {MAX_EMPLOYEES_PER_SHIFT} employees.")
                valid = False
            if len(assigned_names) < MIN_EMPLOYEES_PER_SHIFT:
                print(f"Warning: {day} {shift} has fewer than {MIN_EMPLOYEES_PER_SHIFT} employees.")
                valid = False

            for name in assigned_names:
                if name in seen_names:
                    print(f"Warning: {name} was assigned more than once on {day}.")
                    valid = False
                seen_names.add(name)

    if valid:
        print("All validation checks passed.")


def print_schedule(schedule):
    print("\nFinal Weekly Schedule")
    print("=====================")
    for day in DAYS:
        print(f"\n{day}")
        for shift in SHIFTS:
            names = ", ".join(schedule[day][shift]) if schedule[day][shift] else "Unassigned"
            print(f"{shift}: {names}")


def main():
    employees = build_employees()
    schedule = build_schedule(employees)
    validate_schedule(schedule, employees)
    print_schedule(schedule)


if __name__ == "__main__":
    main()
