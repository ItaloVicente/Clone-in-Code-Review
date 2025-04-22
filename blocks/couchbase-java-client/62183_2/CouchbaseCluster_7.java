        this.environment = environment;
        this.connectionString = connectionString;
        this.bucketCache = new ConcurrentHashMap<String, Bucket>();
        couchbaseAsyncCluster = createAsyncCluster(sharedEnvironment);
    }

    protected CouchbaseAsyncCluster createAsyncCluster(final boolean sharedEnvironment) {
        return new CouchbaseAsyncCluster(
