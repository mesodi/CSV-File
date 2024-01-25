package CSV;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvView {

    public void processCsv() {
        String path = getClass().getClassLoader().getResource("lens-export_5.csv").getPath();
        List<String> processedLines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(";");
                // Kontrollera att arrayen har minst 2 kolumner
                if (columns.length > 1) {
                    columns[0] = columns[0].replace(";", ",");
                    columns[1] = columns[1].replace(";", ",");
                }
                processedLines.add(String.join(",", columns));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Skriv ut eller spara de bearbetade raderna
        processedLines.forEach(System.out::println);
    }
}
