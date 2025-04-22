
    @SuppressWarnings({ "unchecked" })
    public static Observable<Boolean> unlock(final String id, final long cas,
        final CouchbaseEnvironment env, final ClusterFacade core, final String bucket,
        final long timeout, final TimeUnit timeUnit) {
        return Observable.defer(new Func0<Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call() {
                final UnlockRequest request = new UnlockRequest(id, cas, bucket);
                addRequestSpan(env, request, "unlock");
                return applyTimeout(deferAndWatch(new Func1<Subscriber, Observable<UnlockResponse>>() {
                    @Override
                    public Observable<UnlockResponse> call(Subscriber s) {
                        request.subscriber(s);
                        return core.send(request);
                    }
                }).map(new Func1<UnlockResponse, Boolean>() {
                    @Override
                    public Boolean call(UnlockResponse response) {
                        if (response.content() != null && response.content().refCnt() > 0) {
                            response.content().release();
                        }

                        if (env.tracingEnabled()) {
                            env.tracer().scopeManager()
                                .activate(response.request().span(), true)
                                .close();
                        }

                        if (response.status().isSuccess()) {
                            return true;
                        }

                        switch (response.status()) {
                            case NOT_EXISTS:
                                throw addDetails(new DocumentDoesNotExistException(), response);
                            case TEMPORARY_FAILURE:
                            case LOCKED:
                                throw addDetails(new TemporaryLockFailureException(), response);
                            case SERVER_BUSY:
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

    @SuppressWarnings({ "unchecked" })
    public static Observable<Boolean> touch(final String id, final int expiry,
        final CouchbaseEnvironment env, final ClusterFacade core, final String bucket,
        final long timeout, final TimeUnit timeUnit) {
        return Observable.defer(new Func0<Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call() {
                final TouchRequest request = new TouchRequest(id, expiry, bucket);
                addRequestSpan(env, request, "touch");
                return applyTimeout(deferAndWatch(new Func1<Subscriber, Observable<TouchResponse>>() {
                    @Override
                    public Observable<TouchResponse> call(Subscriber s) {
                        request.subscriber(s);
                        return core.send(request);
                    }
                }).map(new Func1<TouchResponse, Boolean>() {
                    @Override
                    public Boolean call(TouchResponse response) {
                        if (response.content() != null && response.content().refCnt() > 0) {
                            response.content().release();
                        }

                        if (env.tracingEnabled()) {
                            env.tracer().scopeManager()
                                .activate(response.request().span(), true)
                                .close();
                        }

                        if (response.status().isSuccess()) {
                            return true;
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
