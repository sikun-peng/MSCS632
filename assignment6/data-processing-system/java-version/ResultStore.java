import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResultStore {
    private final List<String> results = Collections.synchronizedList(new ArrayList<>());

    public void addResult(String result) {
        results.add(result);
    }

    public List<String> getResultsSnapshot() {
        synchronized (results) {
            return new ArrayList<>(results);
        }
    }

    public void writeToFile(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String result : getResultsSnapshot()) {
                writer.write(result);
                writer.newLine();
            }
        }
    }
}
