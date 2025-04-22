    static {
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.PARANOID);
    }

    private static final String seedNode = TestProperties.seedNode();
    private static final String adminName = TestProperties.adminName();
    private static final String adminPassword = TestProperties.adminPassword();

    private static CouchbaseEnvironment env;
    private static Cluster cluster;
    private static Bucket bucket;
    private static ClusterManager clusterManager;
    private static BucketManager bucketManager;
    private static Repository repository;
