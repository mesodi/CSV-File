package CSV;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Map;

public class CSVtoJson {

    public static List<Map<?, ?>> readObjectsFromCsv(File file) throws IOException {
        CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
        CsvMapper csvMapper = new CsvMapper();
        try (MappingIterator<Map<?, ?>> mappingIterator = csvMapper.readerFor(Map.class)
                .with(bootstrapSchema)
                .readValues(file)) {
            return mappingIterator.readAll();
        }
    }

    public static void writeAsJson(List<Map<?, ?>> data, File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, data);
    }

    public static void main(String[] args) {
        // Ange sökvägen till "uploads"-mappen
        String folderPath = "src/main/resources/uploads/";

        try {
            // Hämta en lista över alla filer i mappen
            List<Path> files = Files.list(Paths.get(folderPath)).toList();

            // Loopa igenom alla filer och konvertera dem
            for (Path filePath : files) {
                if (Files.isRegularFile(filePath) && filePath.toString().endsWith(".csv")) {
                    // Om det är en CSV-fil, konvertera den här
                    List<Map<?, ?>> data = readObjectsFromCsv(filePath.toFile());
                    File jsonFile = new File(filePath.toString().replace(".csv", ".json"));
                    writeAsJson(data, jsonFile);
                    System.out.println("Konvertering från CSV till JSON klar för fil: " + filePath);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ett fel inträffade under CSV till JSON konvertering.");
        }
    }
}
