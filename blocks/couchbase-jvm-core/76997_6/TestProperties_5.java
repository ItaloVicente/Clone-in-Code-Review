    private static int mockNodeCount;
    private static int mockReplicaCount;
    private static Bucket.BucketType bucketType;
    private static CouchbaseMock mock;

    private static void createMock() {
        BucketConfiguration bucketConfiguration = new BucketConfiguration();
        bucketConfiguration.numNodes = mockNodeCount;
        bucketConfiguration.numReplicas = mockNodeCount;
        bucketConfiguration.numVBuckets = 1024;
        bucketConfiguration.name = bucket;
        bucketConfiguration.type = bucketType;
        bucketConfiguration.password = password;
        ArrayList<BucketConfiguration> configList = new ArrayList<BucketConfiguration>();
        configList.add(bucketConfiguration);
        try {
            mock = new CouchbaseMock(0, configList);
            mock.start();
            mock.waitForStartup();
        } catch (Exception ex) {
            throw new RuntimeException("Unable to initialize mock" + ex.getMessage(), ex);
        }
    }
