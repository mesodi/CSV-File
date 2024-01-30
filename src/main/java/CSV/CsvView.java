package CSV;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import java.io.Reader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CsvView {

    private static final List<String> EXPECTED_HEADERS = List.of(
            "Publication year",
            "Application Number",
            "Title",
            "Inventors",
            "CPC",
            "Applicants" // Lägg till "Applicants" i listan över förväntade headers
    );

    public Map<String, List<String>> processCsv(String filePath) throws IOException {
        Map<String, List<String>> dataMap = new HashMap<>();

        try (Reader reader = new FileReader(filePath)) {
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim());

            // Initialize lists for expected columns
            EXPECTED_HEADERS.forEach(header -> dataMap.put(header, new ArrayList<>()));

            for (CSVRecord record : parser) {
                for (String header : EXPECTED_HEADERS) {
                    String value = record.isSet(header) ? record.get(header) : "N/A"; // Default to "N/A" if the column is missing
                    dataMap.get(header).add(value);
                }
            }
        }

        return dataMap;
    }
}
