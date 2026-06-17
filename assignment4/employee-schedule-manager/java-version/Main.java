import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Main {
    private static final int MIN_EMPLOYEES_PER_SHIFT = 2;
    private static final int MAX_EMPLOYEES_PER_SHIFT = 3;
    private static final int MAX_DAYS_PER_EMPLOYEE = 5;
    private static final int MIN_TOTAL_ASSIGNMENTS_PER_DAY = MIN_EMPLOYEES_PER_SHIFT * Shift.values().length;
    private static final long RANDOM_SEED = 42L;

    public static void main(String[] args) {
        List<Employee> employees = buildEmployees();
        Map<Day, Map<Shift, List<String>>> schedule = buildSchedule(employees);
        validateSchedule(schedule, employees);
        printSchedule(schedule);
    }

    private static List<Employee> buildEmployees() {
        List<String> names = Arrays.asList(
            "Alice",
            "Bob",
            "Carlos",
            "Diana",
            "Ethan",
            "Fiona",
            "George",
            "Hannah",
            "Ivan",
            "Julia"
        );

        Shift[] basePreferences = {
            Shift.MORNING,
            Shift.MORNING,
            Shift.MORNING,
            Shift.AFTERNOON,
            Shift.AFTERNOON,
            Shift.EVENING,
            Shift.EVENING,
            Shift.MORNING,
            Shift.AFTERNOON,
            Shift.EVENING
        };

        List<Employee> employees = new ArrayList<>();
        Day[] days = Day.values();
        Shift[] shifts = Shift.values();

        for (int index = 0; index < names.size(); index++) {
            Map<Day, Shift> preferences = new EnumMap<>(Day.class);
            for (int dayIndex = 0; dayIndex < days.length; dayIndex++) {
                Shift preference;
                if ((index + dayIndex) % 4 == 0) {
                    preference = Shift.MORNING;
                } else if (dayIndex % 3 == 2) {
                    preference = shifts[(index + 1) % shifts.length];
                } else {
                    preference = basePreferences[index];
                }
                preferences.put(days[dayIndex], preference);
            }
            employees.add(new Employee(names.get(index), preferences));
        }

        return employees;
    }

    private static Map<Day, Map<Shift, List<String>>> buildSchedule(List<Employee> employees) {
        Map<Day, Map<Shift, List<String>>> schedule = initializeSchedule();
        Random random = new Random(RANDOM_SEED);
        Day[] days = Day.values();
        Map<String, Employee> employeesByName = new java.util.HashMap<>();

        for (Employee employee : employees) {
            employeesByName.put(employee.getName(), employee);
        }

        for (int dayIndex = 0; dayIndex < days.length; dayIndex++) {
            Day day = days[dayIndex];
            List<Employee> orderedEmployees = new ArrayList<>(employees);
            orderedEmployees.sort(
                (left, right) -> {
                    int countCompare = Integer.compare(left.getAssignedDayCount(), right.getAssignedDayCount());
                    if (countCompare != 0) {
                        return countCompare;
                    }
                    return left.getName().compareTo(right.getName());
                }
            );

            for (Employee employee : orderedEmployees) {
                if (dayAssignmentCount(schedule, day) >= MIN_TOTAL_ASSIGNMENTS_PER_DAY) {
                    break;
                }
                if (employee.isAssignedOn(day)) {
                    continue;
                }
                if (employee.getAssignedDayCount() >= MAX_DAYS_PER_EMPLOYEE) {
                    continue;
                }

                Shift preferredShift = employee.getPreference(day);
                if (assignEmployee(employee, schedule, day, preferredShift)) {
                    continue;
                }

                if (schedule.get(day).get(preferredShift).size() >= MAX_EMPLOYEES_PER_SHIFT) {
                    System.out.printf(
                        "Conflict detected for %s on %s %s.%n",
                        employee.getName(),
                        day.getDisplayName(),
                        preferredShift.getDisplayName()
                    );
                }

                boolean assigned = false;
                for (Shift shift : Shift.values()) {
                    if (shift == preferredShift) {
                        continue;
                    }
                    if (assignEmployee(employee, schedule, day, shift)) {
                        System.out.printf(
                            "Resolved by assigning %s to %s %s.%n",
                            employee.getName(),
                            day.getDisplayName(),
                            shift.getDisplayName()
                        );
                        assigned = true;
                        break;
                    }
                }

                if (assigned) {
                    continue;
                }

                if (dayIndex == days.length - 1) {
                    System.out.printf(
                        "Warning: no shift available for %s after %s.%n",
                        employee.getName(),
                        day.getDisplayName()
                    );
                    continue;
                }

                Day nextDay = days[dayIndex + 1];
                if (dayAssignmentCount(schedule, nextDay) < MIN_TOTAL_ASSIGNMENTS_PER_DAY) {
                    for (Shift shift : Shift.values()) {
                        if (assignEmployee(employee, schedule, nextDay, shift)) {
                            System.out.printf(
                                "Moved %s to %s %s after conflict on %s.%n",
                                employee.getName(),
                                nextDay.getDisplayName(),
                                shift.getDisplayName(),
                                day.getDisplayName()
                            );
                            assigned = true;
                            break;
                        }
                    }
                }

                if (!assigned) {
                    System.out.printf(
                        "Warning: unable to assign %s on %s or %s.%n",
                        employee.getName(),
                        day.getDisplayName(),
                        nextDay.getDisplayName()
                    );
                }
            }
        }

        for (Day day : Day.values()) {
            rebalanceDay(schedule, employeesByName, day);
        }

        for (Day day : Day.values()) {
            for (Shift shift : Shift.values()) {
                while (schedule.get(day).get(shift).size() < MIN_EMPLOYEES_PER_SHIFT) {
                    List<Employee> eligibleEmployees = new ArrayList<>();
                    for (Employee employee : employees) {
                        if (canAssign(employee, schedule, day, shift)) {
                            eligibleEmployees.add(employee);
                        }
                    }

                    if (eligibleEmployees.isEmpty()) {
                        System.out.printf(
                            "Warning: could not fully staff %s %s; no eligible employees remain.%n",
                            day.getDisplayName(),
                            shift.getDisplayName()
                        );
                        break;
                    }

                    Employee selected = eligibleEmployees.get(random.nextInt(eligibleEmployees.size()));
                    assignEmployee(selected, schedule, day, shift);
                    System.out.printf(
                        "Randomly assigned %s to %s %s.%n",
                        selected.getName(),
                        day.getDisplayName(),
                        shift.getDisplayName()
                    );
                }
            }
        }

        return schedule;
    }

    private static Map<Day, Map<Shift, List<String>>> initializeSchedule() {
        Map<Day, Map<Shift, List<String>>> schedule = new EnumMap<>(Day.class);
        for (Day day : Day.values()) {
            Map<Shift, List<String>> daySchedule = new EnumMap<>(Shift.class);
            for (Shift shift : Shift.values()) {
                daySchedule.put(shift, new ArrayList<>());
            }
            schedule.put(day, daySchedule);
        }
        return schedule;
    }

    private static int dayAssignmentCount(Map<Day, Map<Shift, List<String>>> schedule, Day day) {
        int total = 0;
        for (Shift shift : Shift.values()) {
            total += schedule.get(day).get(shift).size();
        }
        return total;
    }

    private static void rebalanceDay(
        Map<Day, Map<Shift, List<String>>> schedule,
        Map<String, Employee> employeesByName,
        Day day
    ) {
        boolean changed = true;

        while (changed) {
            changed = false;

            for (Shift shift : Shift.values()) {
                if (schedule.get(day).get(shift).size() >= MIN_EMPLOYEES_PER_SHIFT) {
                    continue;
                }

                Shift donorShift = null;
                for (Shift candidateShift : Shift.values()) {
                    if (schedule.get(day).get(candidateShift).size() > MIN_EMPLOYEES_PER_SHIFT) {
                        donorShift = candidateShift;
                        break;
                    }
                }

                if (donorShift == null) {
                    continue;
                }

                List<String> donorNames = schedule.get(day).get(donorShift);
                String selectedName = null;
                for (String name : donorNames) {
                    if (employeesByName.get(name).getPreference(day) == shift) {
                        selectedName = name;
                        break;
                    }
                }

                if (selectedName == null) {
                    selectedName = donorNames.get(donorNames.size() - 1);
                }

                donorNames.remove(selectedName);
                schedule.get(day).get(shift).add(selectedName);
                employeesByName.get(selectedName).reassign(day, shift);
                System.out.printf(
                    "Rebalanced %s from %s %s to %s %s.%n",
                    selectedName,
                    day.getDisplayName(),
                    donorShift.getDisplayName(),
                    day.getDisplayName(),
                    shift.getDisplayName()
                );
                changed = true;
                break;
            }
        }
    }

    private static boolean canAssign(
        Employee employee,
        Map<Day, Map<Shift, List<String>>> schedule,
        Day day,
        Shift shift
    ) {
        if (employee.isAssignedOn(day)) {
            return false;
        }
        if (employee.getAssignedDayCount() >= MAX_DAYS_PER_EMPLOYEE) {
            return false;
        }
        if (schedule.get(day).get(shift).size() >= MAX_EMPLOYEES_PER_SHIFT) {
            return false;
        }
        return true;
    }

    private static boolean assignEmployee(
        Employee employee,
        Map<Day, Map<Shift, List<String>>> schedule,
        Day day,
        Shift shift
    ) {
        if (!canAssign(employee, schedule, day, shift)) {
            return false;
        }
        schedule.get(day).get(shift).add(employee.getName());
        employee.assign(day, shift);
        return true;
    }

    private static void validateSchedule(
        Map<Day, Map<Shift, List<String>>> schedule,
        List<Employee> employees
    ) {
        System.out.println();
        System.out.println("Validation");
        System.out.println("----------");
        boolean valid = true;

        for (Employee employee : employees) {
            if (employee.getAssignedDayCount() > MAX_DAYS_PER_EMPLOYEE) {
                System.out.printf(
                    "Warning: %s works %d days.%n",
                    employee.getName(),
                    employee.getAssignedDayCount()
                );
                valid = false;
            }
        }

        for (Day day : Day.values()) {
            Set<String> seenNames = new HashSet<>();
            for (Shift shift : Shift.values()) {
                List<String> assignedNames = schedule.get(day).get(shift);
                if (assignedNames.size() > MAX_EMPLOYEES_PER_SHIFT) {
                    System.out.printf(
                        "Warning: %s %s exceeds %d employees.%n",
                        day.getDisplayName(),
                        shift.getDisplayName(),
                        MAX_EMPLOYEES_PER_SHIFT
                    );
                    valid = false;
                }
                if (assignedNames.size() < MIN_EMPLOYEES_PER_SHIFT) {
                    System.out.printf(
                        "Warning: %s %s has fewer than %d employees.%n",
                        day.getDisplayName(),
                        shift.getDisplayName(),
                        MIN_EMPLOYEES_PER_SHIFT
                    );
                    valid = false;
                }

                for (String name : assignedNames) {
                    if (seenNames.contains(name)) {
                        System.out.printf(
                            "Warning: %s was assigned more than once on %s.%n",
                            name,
                            day.getDisplayName()
                        );
                        valid = false;
                    }
                    seenNames.add(name);
                }
            }
        }

        if (valid) {
            System.out.println("All validation checks passed.");
        }
    }

    private static void printSchedule(Map<Day, Map<Shift, List<String>>> schedule) {
        System.out.println();
        System.out.println("Final Weekly Schedule");
        System.out.println("=====================");

        for (Day day : Day.values()) {
            System.out.println();
            System.out.println(day.getDisplayName());
            for (Shift shift : Shift.values()) {
                List<String> names = schedule.get(day).get(shift);
                String formattedNames = names.isEmpty() ? "Unassigned" : String.join(", ", names);
                System.out.printf("%s: %s%n", shift.getDisplayName(), formattedNames);
            }
        }
    }
}
