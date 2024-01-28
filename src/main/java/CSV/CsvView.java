package CSV;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.Reader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvView {

    public Map<String, List<String>> processCsv(String filePath) {
        Map<String, List<String>> dataMap = new HashMap<>();
        List<String> applicantsList = new ArrayList<>();
        List<String> inventorsList = new ArrayList<>();

        try (Reader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord csvRecord : csvParser) {
                String applicants = csvRecord.get("Applicants").replace(";", ", ");
                String inventors = csvRecord.get("Inventors").replace(";", ", ");

                applicantsList.add(applicants);
                inventorsList.add(inventors);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        dataMap.put("Applicants", applicantsList);
        dataMap.put("Inventors", inventorsList);
        return dataMap;
    }


}
