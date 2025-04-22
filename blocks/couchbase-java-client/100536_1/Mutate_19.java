                                                       final int expiry, final CouchbaseEnvironment env, final ClusterFacade core,
                                                       final String bucket, final long timeout, final TimeUnit timeUnit, final Span parent) {
        return counter(0, id, delta, initial, expiry, env, core, bucket, timeout, timeUnit, parent);
    }

    public static Observable<JsonLongDocument> counter(final long collectionId, final String id, final long delta, final long initial,
                                                       final int expiry, final CouchbaseEnvironment env, final ClusterFacade core,
                                                       final String bucket, final long timeout, final TimeUnit timeUnit, final Span parent) {
