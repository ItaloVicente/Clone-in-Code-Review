
    public static Observable<JsonLongDocument> counter(final String id, final long delta, final long initial,
        final int expiry, final CouchbaseEnvironment env, final ClusterFacade core,
        final String bucket, final long timeout, final TimeUnit timeUnit) {
        return Observable.defer(new Func0<Observable<JsonLongDocument>>() {
            @Override
            public Observable<JsonLongDocument> call() {
                final CounterRequest request = new CounterRequest(id, initial, delta, expiry, bucket);
                addRequestSpan(env, request, "counter");
                return applyTimeout(deferAndWatch(new Func1<Subscriber, Observable<CounterResponse>>() {
                    @Override
                    public Observable<CounterResponse> call(Subscriber s) {
                        request.subscriber(s);
                        return core.send(request);
                    }
                }).map(new Func1<CounterResponse, JsonLongDocument>() {
                    @Override
                    public JsonLongDocument call(CounterResponse response) {
                        if (response.content() != null && response.content().refCnt() > 0) {
                            response.content().release();
                        }

                        if (env.tracingEnabled()) {
                            env.tracer().scopeManager()
                                .activate(response.request().span(), true)
                                .close();
                        }

                        if (response.status().isSuccess()) {
                            int returnedExpiry = expiry == COUNTER_NOT_EXISTS_EXPIRY ? 0 : expiry;
                            return JsonLongDocument.create(id, returnedExpiry, response.value(),
                                response.cas(), response.mutationToken());
                        }

                        switch (response.status()) {
                            case NOT_EXISTS:
                                throw addDetails(new DocumentDoesNotExistException(), response);
                            case TEMPORARY_FAILURE:
                            case SERVER_BUSY:
                            case LOCKED:
                                throw addDetails(new TemporaryFailureException(), response);
                            case OUT_OF_MEMORY:
                                throw addDetails(new CouchbaseOutOfMemoryException(), response);
                            default:
                                throw addDetails(new CouchbaseException(response.status().toString()), response);
                        }
                    }
                }), request, env, timeout, timeUnit);
            }
        });
    }
