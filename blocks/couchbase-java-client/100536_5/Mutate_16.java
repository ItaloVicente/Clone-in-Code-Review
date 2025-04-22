                                                               final Transcoder<Document<Object>, Object> transcoder,
                                                               final ClusterFacade core, final String bucket,
                                                               final long timeout, final TimeUnit timeUnit, final Span parent) {
        return remove(0, document, env, transcoder, core, bucket, timeout, timeUnit, parent);
    }

    @SuppressWarnings({ "unchecked" })
    public static <D extends Document<?>> Observable<D> remove(final long collectionId, final D document, final CouchbaseEnvironment env,
                                                               final Transcoder<Document<Object>, Object> transcoder,
                                                               final ClusterFacade core, final String bucket,
                                                               final long timeout, final TimeUnit timeUnit, final Span parent) {
