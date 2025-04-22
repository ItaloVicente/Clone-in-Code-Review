    CouchbaseCluster(final CouchbaseEnvironment environment,
        final ConnectionString connectionString, final boolean sharedEnvironment) {
        couchbaseAsyncCluster = new CouchbaseAsyncCluster(
            environment,
            connectionString,
            sharedEnvironment
        );
