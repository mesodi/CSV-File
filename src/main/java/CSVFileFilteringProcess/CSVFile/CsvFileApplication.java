package CSVFileFilteringProcess.CSVFile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"CSVFileFilteringProcess.CSVFile", "CSV"})
public class CsvFileApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsvFileApplication.class, args);
	}
}
