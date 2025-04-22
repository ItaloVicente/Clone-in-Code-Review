                                            final CouchbaseEnvironment env, final ClusterFacade core, final String bucket,
                                            final long timeout, final TimeUnit timeUnit) {
        return touch(0, id, expiry, env, core, bucket, timeout, timeUnit);
    }

    @SuppressWarnings({"unchecked"})
    public static Observable<Boolean> touch(final long collectionId, final String id, final int expiry,
                                            final CouchbaseEnvironment env, final ClusterFacade core, final String bucket,
                                            final long timeout, final TimeUnit timeUnit) {
