import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final int WORKER_COUNT = 3;

    public static void main(String[] args) {
        SharedTaskQueue taskQueue = new SharedTaskQueue();
        ResultStore resultStore = new ResultStore();
        ExecutorService executor = Executors.newFixedThreadPool(WORKER_COUNT);

        try {
            for (int i = 1; i <= 8; i++) {
                taskQueue.addTask(new Task(i, "dataset-" + i));
            }

            for (int i = 1; i <= WORKER_COUNT; i++) {
                executor.submit(new Worker(i, taskQueue, resultStore));
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to submit tasks or workers.", e);
        } finally {
            taskQueue.close();
            executor.shutdown();
        }

        try {
            if (!executor.awaitTermination(15, TimeUnit.SECONDS)) {
                LOGGER.warning("Workers did not finish before timeout. Forcing shutdown.");
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.log(Level.SEVERE, "Main thread interrupted while waiting for workers.", e);
            executor.shutdownNow();
        }

        try {
            resultStore.writeToFile("java_results.txt");
            LOGGER.info("Results written to java_results.txt");
            LOGGER.info("Total results collected: " + resultStore.getResultsSnapshot().size());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to write Java results file.", e);
        }
    }
}
