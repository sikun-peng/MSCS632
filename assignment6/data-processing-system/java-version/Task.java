public class Task {
    private final int taskId;
    private final String data;

    public Task(int taskId, String data) {
        this.taskId = taskId;
        this.data = data;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getData() {
        return data;
    }
}
