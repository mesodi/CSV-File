package CSVFileFilteringProcess.CSVFile.Controller;

import CSV.CsvView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class CsvController {

    private final CsvView csvView;

    @Autowired
    public CsvController(CsvView csvView) {
        this.csvView = csvView;
    }

    @GetMapping("/process-csv")
    public String processCsv() {
        try {
            // Ange sökvägen till din CSV-fil här
            String filePath = "src/main/resources/lens-export1.csv";
            Map<String, List<String>> data = csvView.processCsv(filePath);

            StringBuilder html = new StringBuilder("<html><body>");

            // Dynamiskt skapa HTML-tabeller för varje kolumn i CSV-filen
            for (Map.Entry<String, List<String>> entry : data.entrySet()) {
                html.append("<h1>").append(entry.getKey()).append("</h1><table>");
                for (String value : entry.getValue()) {
                    html.append("<tr><td>").append(value).append("</td></tr>");
                }
                html.append("</table>");
            }

            html.append("</body></html>");

            return html.toString();

        } catch (IOException e) {
            return "Error processing CSV file: " + e.getMessage();
        }
    }
        @PostMapping("/upload-csv")
        public String handleFileUpload(@RequestParam("file") MultipartFile file) {
            if (file.isEmpty()) {
                return "The uploaded file is empty.";
            }

            try {
                // Läs filen och bearbeta innehållet
                Map<String, List<String>> data = csvView.processCsv(file.getInputStream().toString());

                StringBuilder html = new StringBuilder("<html><body>");

                // Dynamiskt skapa HTML-tabeller för varje kolumn i CSV-filen
                for (Map.Entry<String, List<String>> entry : data.entrySet()) {
                    html.append("<h1>").append(entry.getKey()).append("</h1><table>");
                    for (String value : entry.getValue()) {
                        html.append("<tr><td>").append(value).append("</td></tr>");
                    }
                    html.append("</table>");
                }

                html.append("</body></html>");

                return html.toString();

            } catch (IOException e) {
                return "Error processing CSV file: " + e.getMessage();
            }
        }

        @GetMapping("/upload")
        public String uploadPage() {
            // HTML form for file upload
            return "<!DOCTYPE html><html><body>" +
                    "<form method=\"POST\" action=\"/upload-csv\" enctype=\"multipart/form-data\">" +
                    "Choose a file to upload: <input type=\"file\" name=\"file\"><br><br>" +
                    "<input type=\"submit\" value=\"Upload\">" +
                    "</form>" +
                    "</body></html>";
        }
    }

    // Resten av din CsvController-kod...

