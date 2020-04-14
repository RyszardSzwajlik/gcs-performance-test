package pl.ryszardszwajlik.research.gcs.performance.data;

public enum TestBucket {
//    MULTI_REGION_ASIA("performance-test-multi-region-asia"),
//    MULTI_REGION_EU("performance-test-multi-region-eu"),
//    MULTI_REGION_US("performance-test-multi-region-us"),
//    REGION_AUSTRALIA("performance-test-region-australia"),
//    REGION_EUROPE_WEST2("performance-test-region-europe-west2"),
//    REGION_EUROPE_NORTH1("performance-test-region-europe-north1"),
//    REGION_US_WEST1("performance-test-region-us-west-1"),
//    REGION_US_WEST2("performance-test-region-us-west-2"),
//    REGION_US_EAST1("performance-test-region-us-east1"),
    DUAL_REGION_US("performance-test-dual-region-us")
    ;

    private final String bucketName;

    TestBucket(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
