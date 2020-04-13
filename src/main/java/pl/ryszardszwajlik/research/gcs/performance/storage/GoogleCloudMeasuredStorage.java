package pl.ryszardszwajlik.research.gcs.performance.storage;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.ryszardszwajlik.research.gcs.performance.data.TestBucket;
import pl.ryszardszwajlik.research.gcs.performance.data.TestFile;
import pl.ryszardszwajlik.research.gcs.performance.stats.Stat;

@Component
public class GoogleCloudMeasuredStorage implements MeasuredStorage {

    private static final Logger logger = LoggerFactory.getLogger(GoogleCloudMeasuredStorage.class);

    private final Storage storage = StorageOptions.getDefaultInstance().getService();

    private final MultiKeyMap<String, Stat> fileBucketTimeSumMap = new MultiKeyMap<>();

    private final Map<String, Bucket> bucketPool = new ConcurrentHashMap<>();

    @Override
    public boolean storeFile(TestFile file, TestBucket testBucket) {
        Bucket bucket = bucketPool.computeIfAbsent(testBucket.getBucketName(), name -> storage.get(name));

        logger.info("Storing {} into {}", file.getFileName(), testBucket.getBucketName());

        Instant start = Instant.now();
        Blob blob = bucket.create(UUID.randomUUID().toString(), file.getContent());
        Instant stop = Instant.now();

        long duration = Duration.between(start, stop).toMillis();
        addTime(createKey(file, testBucket, "storeFile"), duration, fileBucketTimeSumMap);

        return blob != null;
    }

    @Override
    public MultiKeyMap<String, Stat> getStatistics() {
        return fileBucketTimeSumMap;
    }

    private <T> MultiKey<T> createKey(TestFile file, TestBucket testBucket, String operation) {
        return new MultiKey(file.getFileName(), testBucket.getBucketName(), operation);
    }

    private synchronized void addTime(MultiKey<String> key, long duration, MultiKeyMap<String, Stat> map) {
        boolean containsKey = map.containsKey(key);
        if (!containsKey) {
            fileBucketTimeSumMap.put(key, new Stat());
        }
        fileBucketTimeSumMap.get(key).add(duration);
    }
}
