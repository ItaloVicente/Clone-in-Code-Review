    private static final TimeUnit TIMEOUT_UNIT = TimeUnit.MILLISECONDS;
    private final AsyncBucket asyncBucket;
    private final CouchbaseEnvironment environment;
    private final long binaryTimeout;
    private final String name;
    private final String password;
    private final ClusterFacade core;
