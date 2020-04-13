package pl.ryszardszwajlik.research.gcs.performance;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.ryszardszwajlik.research.gcs.performance.data.TestBucket;
import pl.ryszardszwajlik.research.gcs.performance.data.TestFile;
import pl.ryszardszwajlik.research.gcs.performance.storage.MeasuredStorage;

@SpringBootApplication
public class PerformanceApplication {

	private static final Logger logger = LoggerFactory.getLogger(PerformanceApplication.class);

	private final MeasuredStorage measuredStorage;

	public PerformanceApplication(MeasuredStorage measuredStorage) {
		this.measuredStorage = measuredStorage;
	}

	public static void main(String[] args) {
		SpringApplication.run(PerformanceApplication.class, args);
	}

	@PostConstruct
	void performTest() {
		boolean stored = measuredStorage.storeFile(TestFile.FILE_1MB_TEXT, TestBucket.REGION_EUROPE_WEST2);
		if (stored) {
			logger.info("File stored successfully");
		} else {
			logger.error("An error occured storing the file");
		}
	}

}
