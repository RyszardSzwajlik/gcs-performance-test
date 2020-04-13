package pl.ryszardszwajlik.research.gcs.performance;

import javax.annotation.PostConstruct;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.ryszardszwajlik.research.gcs.performance.data.TestBucket;
import pl.ryszardszwajlik.research.gcs.performance.data.TestFile;
import pl.ryszardszwajlik.research.gcs.performance.stats.MultiKeyMapPrinter;
import pl.ryszardszwajlik.research.gcs.performance.stats.Stat;
import pl.ryszardszwajlik.research.gcs.performance.storage.MeasuredStorage;

@SpringBootApplication
public class PerformanceApplication {

    @Value("${repeats}")
    private Integer repeats;

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
    void performTest() throws InterruptedException {
//        ExecutorService executorService = Executors.newFixedThreadPool(4);
//        List<Callable<Boolean>> callables = new ArrayList<>();
        for (int i = 0; i < repeats; i++) {
            for (TestFile testFile : TestFile.values()) {
                for (TestBucket testBucket : TestBucket.values()) {
                    measuredStorage.storeFile(testFile, testBucket);
                }
            }
        }
//        executorService.invokeAll(callables);

        MultiKeyMap<String, Stat> statistics = measuredStorage.getStatistics();

        multiKeyMapPrinter.print(statistics, "storeFile");
    }

}
