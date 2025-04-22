
    @SuppressWarnings({ "unchecked" })
    public static <D extends Document<?>> Observable<D> append(final D document, final CouchbaseEnvironment env,
        final Transcoder<Document<Object>, Object> transcoder, final ClusterFacade core, final String bucket,
        final long timeout, final TimeUnit timeUnit) {
        return Observable.defer(new Func0<Observable<D>>() {
            @Override
            public Observable<D> call() {
                Span requestSpan = null;
                if (env.tracingEnabled()) {
                    Scope scope = env.tracer()
                        .buildSpan("append")
                        .startActive(false);
                    requestSpan = scope.span();
                    scope.close();
                }

                Scope encodeScope = null;
                if (requestSpan != null) {
                    encodeScope = env.tracer()
                        .buildSpan("request_encoding")
                        .asChildOf(requestSpan)
                        .startActive(true);
                }

                Tuple2<ByteBuf, Integer> encoded = transcoder.encode((Document<Object>) document);

                if (encodeScope != null) {
                    encodeScope.close();
                    if (encodeScope.span() instanceof ThresholdLogSpan) {
                        encodeScope.span().setBaggageItem(ThresholdLogReporter.KEY_ENCODE_MICROS,
                            Long.toString(((ThresholdLogSpan) encodeScope.span()).durationMicros())
                        );
                    }
                }

                final AppendRequest request = new AppendRequest(
                    document.id(), document.cas(), encoded.value1(), bucket
                );

                if (requestSpan != null) {
                    request.span(requestSpan, env);
                }
                return applyTimeout(deferAndWatch(new Func1<Subscriber, Observable<AppendResponse>>() {
                    @Override
                    public Observable<AppendResponse> call(Subscriber s) {
                        request.subscriber(s);
                        return core.send(request);
                    }
                }).map(new Func1<AppendResponse, D>() {
                    @Override
                    public D call(final AppendResponse response) {
                        if (response.content() != null && response.content().refCnt() > 0) {
                            response.content().release();
                        }

                        if (response.status().isSuccess()) {
                            return (D) transcoder.newDocument(document.id(), 0, null, response.cas(), response.mutationToken());
                        }

                        switch (response.status()) {
                            case TOO_BIG:
                                throw addDetails(new RequestTooBigException(), response);
                            case NOT_STORED:
                                throw addDetails(new DocumentDoesNotExistException(), response);
                            case TEMPORARY_FAILURE:
                            case SERVER_BUSY:
                            case LOCKED:
                                throw addDetails(new TemporaryFailureException(), response);
                            case OUT_OF_MEMORY:
                                throw addDetails(new CouchbaseOutOfMemoryException(), response);
                            case EXISTS:
                                throw addDetails(new CASMismatchException(), response);
                            default:
                                throw addDetails(new CouchbaseException(response.status().toString()), response);
                        }
                    }
                }), request, env, timeout, timeUnit);
            }
        });
    }

    @SuppressWarnings({ "unchecked" })
    public static <D extends Document<?>> Observable<D> prepend(final D document, final CouchbaseEnvironment env,
        final Transcoder<Document<Object>, Object> transcoder, final ClusterFacade core, final String bucket,
        final long timeout, final TimeUnit timeUnit) {
        return Observable.defer(new Func0<Observable<D>>() {
            @Override
            public Observable<D> call() {
                Span requestSpan = null;
                if (env.tracingEnabled()) {
                    Scope scope = env.tracer()
                        .buildSpan("prepend")
                        .startActive(false);
                    requestSpan = scope.span();
                    scope.close();
                }

                Scope encodeScope = null;
                if (requestSpan != null) {
                    encodeScope = env.tracer()
                        .buildSpan("request_encoding")
                        .asChildOf(requestSpan)
                        .startActive(true);
                }

                Tuple2<ByteBuf, Integer> encoded = transcoder.encode((Document<Object>) document);

                if (encodeScope != null) {
                    encodeScope.close();
                    if (encodeScope.span() instanceof ThresholdLogSpan) {
                        encodeScope.span().setBaggageItem(ThresholdLogReporter.KEY_ENCODE_MICROS,
                            Long.toString(((ThresholdLogSpan) encodeScope.span()).durationMicros())
                        );
                    }
                }

                final PrependRequest request = new PrependRequest(
                    document.id(), document.cas(), encoded.value1(), bucket
                );

                if (requestSpan != null) {
                    request.span(requestSpan, env);
                }
                return applyTimeout(deferAndWatch(new Func1<Subscriber, Observable<PrependResponse>>() {
                    @Override
                    public Observable<PrependResponse> call(Subscriber s) {
                        request.subscriber(s);
                        return core.send(request);
                    }
                }).map(new Func1<PrependResponse, D>() {
                    @Override
                    public D call(final PrependResponse response) {
                        if (response.content() != null && response.content().refCnt() > 0) {
                            response.content().release();
                        }

                        if (response.status().isSuccess()) {
                            return (D) transcoder.newDocument(document.id(), 0, null, response.cas(), response.mutationToken());
                        }

                        switch (response.status()) {
                            case TOO_BIG:
                                throw addDetails(new RequestTooBigException(), response);
                            case NOT_STORED:
                                throw addDetails(new DocumentDoesNotExistException(), response);
                            case TEMPORARY_FAILURE:
                            case SERVER_BUSY:
                            case LOCKED:
                                throw addDetails(new TemporaryFailureException(), response);
                            case OUT_OF_MEMORY:
                                throw addDetails(new CouchbaseOutOfMemoryException(), response);
                            case EXISTS:
                                throw addDetails(new CASMismatchException(), response);
                            default:
                                throw addDetails(new CouchbaseException(response.status().toString()), response);
                        }
                    }
                }), request, env, timeout, timeUnit);
            }
        });
    }
