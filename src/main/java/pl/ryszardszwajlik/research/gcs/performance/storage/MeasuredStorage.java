package pl.ryszardszwajlik.research.gcs.performance.storage;

import pl.ryszardszwajlik.research.gcs.performance.data.TestBucket;
import pl.ryszardszwajlik.research.gcs.performance.data.TestFile;

public interface MeasuredStorage {

    boolean storeFile(TestFile file, TestBucket testBucket);

}
