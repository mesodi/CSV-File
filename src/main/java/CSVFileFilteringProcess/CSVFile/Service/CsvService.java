package CSVFileFilteringProcess.CSVFile.Service;

import CSV.CsvView;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Service
public class CsvService {

    private final CsvView csvView;

    public CsvService(CsvView csvView) {
        this.csvView = csvView;
    }

    public String processUploadedFile(MultipartFile file) {
        return processUploadedFile(file, null); // Anropa den andra versionen av metoden utan sökterm
    }

    public String processUploadedFile(MultipartFile file, String searchTerm) {
        try {
            // Skapa en temporär fil
            Path tempFilePath = Files.createTempFile("upload-", ".csv");
            // Spara den uppladdade filen till den temporära filen
            file.transferTo(tempFilePath.toFile());

            // Anropa CsvView för att bearbeta filen och returnera resultatet
            Map<String, List<String>> data = csvView.processCsv(tempFilePath.toString(), searchTerm);

            // Konvertera data till HTML eller vad du vill göra med det
            StringBuilder html = new StringBuilder("<html><body>");
            // ... Här kan du generera HTML-baserat på ditt filtrerade data ...
            html.append("</body></html>");

            return html.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error during file processing: " + e.getMessage();
        }
    }
}
