                                                            final CouchbaseEnvironment environment, final String bucket, final ClusterFacade core,
                                                            final Map<Class<? extends Document>, Transcoder<? extends Document, ?>> transcoders,
                                                            final long timeout, final TimeUnit timeUnit) {
        return get(0, id, target, environment, bucket, core, transcoders, timeout, timeUnit);
    }

    public static <D extends Document<?>> Observable<D> get(final long collectionId, final String id, final Class<D> target,
                                                            final CouchbaseEnvironment environment,
                                                            final String bucket, final ClusterFacade core,
                                                            final Map<Class<? extends Document>, Transcoder<? extends Document, ?>> transcoders,
                                                            final long timeout, final TimeUnit timeUnit) {
