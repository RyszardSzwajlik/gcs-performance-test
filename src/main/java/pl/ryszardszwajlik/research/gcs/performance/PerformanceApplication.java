package pl.ryszardszwajlik.research.gcs.performance;

import javax.annotation.PostConstruct;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.ryszardszwajlik.research.gcs.performance.data.TestBucket;
import pl.ryszardszwajlik.research.gcs.performance.data.TestFile;
import pl.ryszardszwajlik.research.gcs.performance.stats.MultiKeyMapPrinter;
import pl.ryszardszwajlik.research.gcs.performance.storage.MeasuredStorage;
import pl.ryszardszwajlik.research.gcs.performance.stats.Stat;

@SpringBootApplication
public class PerformanceApplication {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceApplication.class);

    private final MeasuredStorage measuredStorage;
    private final MultiKeyMapPrinter multiKeyMapPrinter;

    public PerformanceApplication(MeasuredStorage measuredStorage,
            MultiKeyMapPrinter multiKeyMapPrinter) {
        this.measuredStorage = measuredStorage;
        this.multiKeyMapPrinter = multiKeyMapPrinter;
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

        MultiKeyMap<String, Stat> statistics = measuredStorage.getStatistics();

        multiKeyMapPrinter.print(statistics);
    }


}
