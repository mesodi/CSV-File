package CSV;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CsvView {

    public Map<String, List<String>> processCsv(String filePath) {
        return processCsv(filePath, null); // Anropa den andra versionen av metoden utan sökterm
    }

    public Map<String, List<String>> processCsv(String filePath, String searchTerm) {
        Map<String, List<String>> dataMap = new HashMap<>();
        List<String> applicantsList = new ArrayList<>();
        List<String> inventorsList = new ArrayList<>();

        try (Reader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord csvRecord : csvParser) {
                String applicants = csvRecord.get("Applicants");
                String inventors = csvRecord.get("Inventors");

                // Filtrera rader baserat på söktermen (searchTerm)
                if (searchTerm == null || searchTerm.isEmpty() || applicants.toLowerCase().contains(searchTerm.toLowerCase()) || inventors.toLowerCase().contains(searchTerm.toLowerCase())) {
                    applicantsList.add(applicants);
                    inventorsList.add(inventors);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        dataMap.put("Applicants", applicantsList);
        dataMap.put("Inventors", inventorsList);
        return dataMap;
    }

    public List<String> searchInCsv(String searchTerm, Map<String, List<String>> data) {
        List<String> searchResults = new ArrayList<>();
        data.forEach((key, values) -> {
            for (String value : values) {
                if (value.toLowerCase().contains(searchTerm.toLowerCase())) {
                    searchResults.add(value);
                }
            }
        });
        return searchResults;
    }
}
