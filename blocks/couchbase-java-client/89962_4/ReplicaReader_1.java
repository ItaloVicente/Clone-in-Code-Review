    public static <D extends Document<?>> Observable<D> read(final ClusterFacade core, final String id,
        final ReplicaMode type, final String bucket,
        final Map<Class<? extends Document>, Transcoder<? extends Document, ?>> transcoders, final Class<D> target,
        final CouchbaseEnvironment environment, final long timeout, final TimeUnit timeUnit) {

        return Observable.defer(new Func0<Observable<D>>() {
            @Override
            public Observable<D> call() {
                final Span parentSpan;
                if (environment.tracingEnabled()) {
                    Scope scope = environment.tracer()
                      .buildSpan("get_from_replica")
                      .startActive(false);
                    parentSpan = scope.span();
                    scope.close();
                } else {
                    parentSpan = null;
