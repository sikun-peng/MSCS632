import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Worker implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(Worker.class.getName());

    private final int workerId;
    private final SharedTaskQueue taskQueue;
    private final ResultStore resultStore;

    public Worker(int workerId, SharedTaskQueue taskQueue, ResultStore resultStore) {
        this.workerId = workerId;
        this.taskQueue = taskQueue;
        this.resultStore = resultStore;
    }

    @Override
    public void run() {
        LOGGER.info(() -> "Worker-" + workerId + " started.");

        try {
            while (true) {
                Task task = taskQueue.getTask();
                if (task == null) {
                    LOGGER.info(() -> "Worker-" + workerId + " completed all tasks.");
                    return;
                }

                String result = processTask(task);
                resultStore.addResult(result);
                LOGGER.info(() -> "Worker-" + workerId + " saved result for Task-" + task.getTaskId());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.log(Level.SEVERE, "Worker-" + workerId + " interrupted.", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Worker-" + workerId + " encountered an error.", e);
        }
    }

    private String processTask(Task task) throws InterruptedException {
        LOGGER.info(() -> "Worker-" + workerId + " processing Task-" + task.getTaskId());

        if (task.getData() == null || task.getData().isBlank()) {
            throw new IllegalArgumentException("Task data cannot be empty.");
        }

        Thread.sleep(300L + (task.getTaskId() * 50L));

        String processed = task.getData().toUpperCase(Locale.ROOT);
        return "Task-" + task.getTaskId() + " processed by Worker-" + workerId + ": " + processed;
    }
}
