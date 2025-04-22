    public static CouchbaseAsyncCluster fromConnectionString(final CouchbaseEnvironment environment,
        final String connectionString) {
        return new CouchbaseAsyncCluster(
            environment,
            ConnectionString.create(connectionString),
            true
        );
