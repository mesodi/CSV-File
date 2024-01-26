package CSV;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvView {

    public Map<String, List<String>> processCsv() {
        String path = getClass().getClassLoader().getResource("lens-export_5.csv").getPath();
        Map<String, List<String>> dataMap = new HashMap<>();
        List<String> applicantsList = new ArrayList<>();
        List<String> inventorsList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            br.readLine(); // Skip header if necessary
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(";");
                if (columns.length > 6) {
                    applicantsList.add(columns[5].replace(";", ","));
                    inventorsList.add(columns[6].replace(";", ","));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        dataMap.put("Applicants", applicantsList);
        dataMap.put("Inventors", inventorsList);
        return dataMap;
    }
}

