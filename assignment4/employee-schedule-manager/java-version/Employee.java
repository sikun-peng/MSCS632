import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Employee {
    private final String name;
    private final Map<Day, Shift> preferences;
    private final Set<Day> assignedDays;
    private final Map<Day, Shift> assignments;

    public Employee(String name, Map<Day, Shift> preferences) {
        this.name = name;
        this.preferences = preferences;
        this.assignedDays = new HashSet<>();
        this.assignments = new EnumMap<>(Day.class);
    }

    public String getName() {
        return name;
    }

    public Shift getPreference(Day day) {
        return preferences.get(day);
    }

    public boolean isAssignedOn(Day day) {
        return assignedDays.contains(day);
    }

    public int getAssignedDayCount() {
        return assignedDays.size();
    }

    public void assign(Day day, Shift shift) {
        assignedDays.add(day);
        assignments.put(day, shift);
    }

    public void reassign(Day day, Shift shift) {
        assignments.put(day, shift);
    }
}
