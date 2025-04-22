                                                                    final CouchbaseEnvironment environment, final String bucket,
                                                                    final ClusterFacade core,
                                                                    final Map<Class<? extends Document>, Transcoder<? extends Document, ?>> transcoders,
                                                                    final int expiry, final long timeout, final TimeUnit timeUnit) {
        return getAndTouch(0, id, target, environment, bucket, core, transcoders, expiry, timeout, timeUnit);
    }

    public static <D extends Document<?>> Observable<D> getAndTouch(final long collectionId, final String id, final Class<D> target,
                                                                    final CouchbaseEnvironment environment, final String bucket,
                                                                    final ClusterFacade core,
                                                                    final Map<Class<? extends Document>, Transcoder<? extends Document, ?>> transcoders,
                                                                    final int expiry, final long timeout, final TimeUnit timeUnit) {
