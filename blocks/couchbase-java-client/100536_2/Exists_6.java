                                             final ClusterFacade core, final String bucket, final long timeout,
                                             final TimeUnit timeUnit) {
        return exists(0, id, environment, core, bucket, timeout, timeUnit);
    }
    public static Observable<Boolean> exists(final long collectionId, final String id, final CouchbaseEnvironment environment,
                                             final ClusterFacade core, final String bucket, final long timeout,
                                             final TimeUnit timeUnit) {
