                                             final CouchbaseEnvironment env, final ClusterFacade core, final String bucket,
                                             final long timeout, final TimeUnit timeUnit) {
        return unlock(0, id, cas, env, core, bucket, timeout, timeUnit);
    }

    @SuppressWarnings({"unchecked"})
    public static Observable<Boolean> unlock(final long collectionId, final String id, final long cas,
                                             final CouchbaseEnvironment env, final ClusterFacade core, final String bucket,
                                             final long timeout, final TimeUnit timeUnit) {
