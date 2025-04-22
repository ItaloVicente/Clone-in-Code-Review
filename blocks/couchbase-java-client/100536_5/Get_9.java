                                                                   final CouchbaseEnvironment environment, final String bucket,
                                                                   final ClusterFacade core,
                                                                   final Map<Class<? extends Document>, Transcoder<? extends Document, ?>> transcoders, final int lockTime,
                                                                   final long timeout, final TimeUnit timeUnit) {
        return getAndLock(0, id, target, environment, bucket, core, transcoders, lockTime, timeout, timeUnit);
    }

    public static <D extends Document<?>> Observable<D> getAndLock(final long collectionId, final String id, final Class<D> target,
                                                                   final CouchbaseEnvironment environment, final String bucket,
                                                                   final ClusterFacade core,
                                                                   final Map<Class<? extends Document>, Transcoder<? extends Document, ?>> transcoders, final int lockTime,
                                                                   final long timeout, final TimeUnit timeUnit) {
