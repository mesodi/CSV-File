package CSVFileFilteringProcess.CSVFile.Controller;

import CSV.CsvView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CsvController {
    @GetMapping("/process-csv")
    public String processCsv() {
        CsvView csvView = new CsvView();
        csvView.processCsv();
        return "CSV processing done";
    }
}

