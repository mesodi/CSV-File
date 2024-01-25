package CSVFileFilteringProcess.CSVFile;

import CSV.CsvView;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CsvFileApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsvFileApplication.class, args);
		CsvView csvView = new CsvView();
		csvView.processCsv();
	}

}
