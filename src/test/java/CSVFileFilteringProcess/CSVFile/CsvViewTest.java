package CSVFileFilteringProcess.CSVFile;


import static org.assertj.core.api.Assertions.assertThat;

import CSV.CsvView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class CsvViewTest {

    @Autowired
    private CsvView csvView;

    @Test
    public void whenProcessCsv_thenExpectCorrectDataMap(@TempDir Path tempDir) throws IOException {
        // Skapa en tillfällig CSV-fil
        Path csvFile = tempDir.resolve("test.csv");
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(csvFile))) {
            writer.println("Publication year,Application Number,Title,Inventors,CPC,Applicants");
            writer.println("2020,123456,Some Title,Inventor Name,CPC Code,Applicant Name");
        }

        // Bearbeta den tillfälliga CSV-filen
        Map<String, List<String>> dataMap = csvView.processCsv(csvFile.toString());

        // Kontrollera att dataMap innehåller de korrekta rubrikerna och datan
        assertThat(dataMap.get("Publication year")).containsExactly("2020");
        assertThat(dataMap.get("Application Number")).containsExactly("123456");
        assertThat(dataMap.get("Title")).containsExactly("Some Title");
        assertThat(dataMap.get("Inventors")).containsExactly("Inventor Name");
        assertThat(dataMap.get("CPC")).containsExactly("CPC Code");
        assertThat(dataMap.get("Applicants")).containsExactly("Applicant Name");
    }
}
