package CSVFileFilteringProcess.CSVFile.Service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

    @Service
    public class FileProcessingTask {

        private static final String UPLOAD_DIR = "src/main/resources/uploads/";

        @Scheduled(fixedRate = 60000)
        public void processUploadedFiles() {
            try (Stream<Path> files = Files.list(Paths.get(UPLOAD_DIR))) {
                files.filter(Files::isRegularFile)
                        .filter(path -> path.toString().endsWith(".csv"))
                        .forEach(this::processFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void processFile(Path path) {
            // Din logik för att bearbeta varje CSV-fil här
            System.out.println("Bearbetar fil: " + path);
            // Efter bearbetning, flytta eller ta bort filen för att undvika att bearbeta den igen
        }
    }

