    public static <D extends Document<?>> Observable<D> read(final long collectionId, final ClusterFacade core, final String id,
                                                             final ReplicaMode type, final String bucket,
                                                             final Map<Class<? extends Document>, Transcoder<? extends Document, ?>> transcoders, final Class<D> target,
                                                             final CouchbaseEnvironment environment, final long timeout, final TimeUnit timeUnit) {

        return Observable.defer(new Func0<Observable<D>>() {
            @Override
            public Observable<D> call() {
                final Span parentSpan;
                if (environment.operationTracingEnabled()) {
                    Scope scope = environment.tracer()
                            .buildSpan("get_from_replica")
                            .startActive(false);
                    parentSpan = scope.span();
                    scope.close();
                } else {
                    parentSpan = null;
                }

                Observable<D> result = assembleRequests(collectionId, core, id, type, bucket)
                        .flatMap(new Func1<BinaryRequest, Observable<D>>() {
                            @Override
                            public Observable<D> call(final BinaryRequest request) {
                                String name = request instanceof ReplicaGetRequest ? "get_replica" : "get";
                                addRequestSpanWithParent(environment, parentSpan, request, name);

                                Observable<GetResponse> result = deferAndWatch(new Func1<Subscriber, Observable<GetResponse>>() {
                                    @Override
                                    public Observable<GetResponse> call(Subscriber subscriber) {
                                        request.subscriber(subscriber);
                                        return core.send(request);
                                    }
                                }).filter(new Get.GetFilter(environment));

                                if (timeout > 0) {
                                    result = result.timeout(timeout, timeUnit, environment.scheduler());
                                }

                                return result.onErrorResumeNext(GetResponseErrorHandler.INSTANCE)
                                        .map(new Get.GetMap(environment, transcoders, target, id));
                            }
                        });

                if (timeout > 0) {
                    result = result.timeout(timeout, timeUnit, environment.scheduler());
                }

                return result.doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        if (environment.operationTracingEnabled() && parentSpan != null) {
                            environment.tracer().scopeManager()
                                    .activate(parentSpan, true)
                                    .close();
                        }
                    }
                })
                        .cacheWithInitialCapacity(type.maxAffectedNodes());
            }
        });
    }

