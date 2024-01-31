package CSVFileFilteringProcess.CSVFile.Controller;

import CSV.CsvView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class CsvController {

    @GetMapping("/process-csv")
    public String processCsv() {
        CsvView csvView = new CsvView();
        StringBuilder html = new StringBuilder("<html><body>");

        // Antag att vi har 5 filer att bearbeta
        for (int i = 1; i <= 5; i++) {
            String filePath = "src/main/resources/lens-expor.csv";
            Map<String, List<String>> data = csvView.processCsv(filePath);

            // Bygg HTML för varje fil
            html.append("<h1>Data from file: ").append(filePath).append("</h1>");

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
        }

        html.append("</body></html>");
        return html.toString();
    }
}
