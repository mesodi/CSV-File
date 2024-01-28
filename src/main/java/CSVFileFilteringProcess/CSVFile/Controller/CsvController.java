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
        // Ange sökvägen till din CSV-fil här
        String filePath = "src/main/resources/lens-export_5.csv";
        Map<String, List<String>> data = csvView.processCsv(filePath);

        StringBuilder html = new StringBuilder("<html><body>");

        // För Applicants
        html.append("<h1>Applicants</h1><table>");
        for (String applicant : data.get("Applicants")) {
            // Splitta namnen på radbrytningar
            String[] applicantNames = applicant.split("\\n");
            for (String name : applicantNames) {
                html.append("<tr><td>").append(name).append("</td></tr>");
            }
        }
        html.append("</table>");

        // För Inventors
        html.append("<h1>Inventors</h1><table>");
        for (String inventor : data.get("Inventors")) {
            // Splitta namnen på radbrytningar
            String[] inventorNames = inventor.split("\\n");
            for (String name : inventorNames) {
                html.append("<tr><td>").append(name).append("</td></tr>");
            }
        }
        html.append("</table></body></html>");

        return html.toString();
    }
}
