package pl.ryszardszwajlik.research.gcs.performance.storage;

import org.apache.commons.collections4.map.MultiKeyMap;
import pl.ryszardszwajlik.research.gcs.performance.data.TestBucket;
import pl.ryszardszwajlik.research.gcs.performance.data.TestFile;
import pl.ryszardszwajlik.research.gcs.performance.stats.Stat;

public interface MeasuredStorage {

    boolean storeFile(TestFile file, TestBucket testBucket);

    MultiKeyMap<String, Stat> getStatistics();
}
