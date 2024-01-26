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
        Map<String, List<String>> data = csvView.processCsv();

        StringBuilder html = new StringBuilder("<html><body>");

        html.append("<h1>Applicants</h1><table>");
        for (String applicant : data.get("Applicants")) {
            html.append("<tr><td>").append(applicant).append("</td></tr>");
        }
        html.append("</table>");

        html.append("<h1>Inventors</h1><table>");
        for (String inventor : data.get("Inventors")) {
            html.append("<tr><td>").append(inventor).append("</td></tr>");
        }
        html.append("</table></body></html>");

        return html.toString();
    }}
