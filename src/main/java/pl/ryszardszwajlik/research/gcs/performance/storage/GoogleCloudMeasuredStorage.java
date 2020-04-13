package pl.ryszardszwajlik.research.gcs.performance.storage;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.util.UUID;
import org.springframework.stereotype.Component;
import pl.ryszardszwajlik.research.gcs.performance.data.TestBucket;
import pl.ryszardszwajlik.research.gcs.performance.data.TestFile;

@Component
public class GoogleCloudMeasuredStorage implements MeasuredStorage {

    private final Storage storage = StorageOptions.getDefaultInstance().getService();
//    private final Storage storage;

    public GoogleCloudMeasuredStorage() {
//        try {
//            GoogleCredentials credentials = GoogleCredentials
//                    .fromStream(new FileInputStream("/home/ryszard/private_gcs_credentials.json"));
//            StorageOptions storageOptions = StorageOptions.newBuilder().setCredentials(credentials).build();
//            storage = storageOptions.getService();
//        } catch (IOException e) {
//            throw new RuntimeException("Can't read file from property GOOGLE_APPLICATION_CREDENTIALS", e);
//        }
    }

    @Override
    public boolean storeFile(TestFile file, TestBucket testBucket) {
        Bucket bucket = storage.get(testBucket.getBucketName());
        Blob blob = bucket.create(UUID.randomUUID().toString(), file.getContent());
        return blob != null;
    }
}
