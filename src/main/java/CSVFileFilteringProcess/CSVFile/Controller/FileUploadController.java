package CSVFileFilteringProcess.CSVFile.Controller;

import CSVFileFilteringProcess.CSVFile.Service.CsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileUploadController {

    private final CsvService csvService;

    @Autowired
    public FileUploadController(CsvService csvService) {
        this.csvService = csvService;
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("files") List<MultipartFile> files, @RequestParam(value = "searchTerm", required = false) String searchTerm) {
        StringBuilder response = new StringBuilder();

        for (MultipartFile file : files) {
            String result = csvService.processUploadedFile(file, searchTerm);
            response.append(result).append("\n");
        }

        return response.toString();
    }
}
