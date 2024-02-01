package CSVFileFilteringProcess.CSVFile.Controller;

import CSV.CsvView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

@RestController
public class CsvController {

    private final CsvView csvView;
    private final ResourceLoader resourceLoader;

    @Autowired
    public CsvController(CsvView csvView, ResourceLoader resourceLoader) {
        this.csvView = csvView;
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/process-csv")
    public String processCsv() {
        StringBuilder html = new StringBuilder("<html><body>");

        // Hämta resursen som representerar mappen "uploads"
        String uploadsFolderPath = "src/main/resources/uploads/";
        File uploadsFolder = new File(uploadsFolderPath);

        // Kontrollera om mappen "uploads" finns
        if (uploadsFolder.exists() && uploadsFolder.isDirectory()) {
            File[] csvFiles = uploadsFolder.listFiles((dir, name) -> name.endsWith(".csv"));

            if (csvFiles != null) {
                for (File csvFile : csvFiles) {
                    // Hämta filvägen till CSV-filen
                    String filePath = csvFile.getAbsolutePath();
                    Map<String, List<String>> data = csvView.processCsv(filePath);

                    // Bygg HTML för varje fil
                    html.append("<h1>Data from file: ").append(csvFile.getName()).append("</h1>");

                    // För Applicants
                    html.append("<h2>Applicants</h2><table>");
                    for (String applicant : data.get("Applicants")) {
                        // Splitta namnen på radbrytningar
                        String[] applicantNames = applicant.split("\\n");
                        for (String name : applicantNames) {
                            html.append("<tr><td>").append(name).append("</td></tr>");
                        }
                    }
                    html.append("</table>");

                    // För Inventors
                    html.append("<h2>Inventors</h2><table>");
                    for (String inventor : data.get("Inventors")) {
                        // Splitta namnen på radbrytningar
                        String[] inventorNames = inventor.split("\\n");
                        for (String name : inventorNames) {
                            html.append("<tr><td>").append(name).append("</td></tr>");
                        }
                    }
                    html.append("</table>");

                    // Lägg till länk för att ladda ner JSON-filen
                    String jsonFileName = csvFile.getName().replace(".csv", ".json");
                    html.append("<a href=\"/download-json/").append(jsonFileName).append("\">Download JSON</a>");
                }
            }
        } else {
            html.append("<h1>No 'uploads' folder found.</h1>");
        }

        html.append("</body></html>");
        return html.toString();
    }

    @GetMapping("/download-json/{fileName}")
    public ResponseEntity<byte[]> downloadJson(@PathVariable String fileName) {
        // Bygg sökvägen till JSON-filen
        String jsonFilePath = "src/main/resources/uploads/" + fileName + ".json";
        File jsonFile = new File(jsonFilePath);

        if (jsonFile.exists()) {
            try {
                // Läs JSON-filen som en byte-array
                byte[] jsonBytes = Files.readAllBytes(jsonFile.toPath());

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setContentDispositionFormData("attachment", fileName + ".json");
                headers.setContentLength(jsonBytes.length);

                // Returnera JSON-filen som en ResponseEntity
                return ResponseEntity.ok()
                        .headers(headers)
                        .body(jsonBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Om filen inte hittades, returnera ett felmeddelande
        return ResponseEntity.notFound().build();
    }
}
