package CSVFileFilteringProcess.CSVFile.Service;

import CSV.CsvView;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class CsvService {

    private final CsvView csvView;

    public CsvService(CsvView csvView) {
        this.csvView = csvView;
    }

    public String processUploadedFile(MultipartFile file) {
        try {
            // Skapa en temporär fil
            Path tempFilePath = Files.createTempFile("upload-", ".csv");
            // Spara den uppladdade filen till den temporära filen
            file.transferTo(tempFilePath.toFile());

            // Anropa CsvView för att bearbeta filen och returnera resultatet
            return csvView.processCsv(tempFilePath.toString()).toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error during file processing: " + e.getMessage();
        }
    }
}