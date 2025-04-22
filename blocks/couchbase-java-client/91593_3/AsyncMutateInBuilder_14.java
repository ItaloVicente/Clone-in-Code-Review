                    }
                }), request, environment, timeout, timeUnit);
            }
        });
    }

    private Observable<DocumentFragment<Mutation>> removeIn(final MutationSpec spec, final long timeout, final TimeUnit timeUnit) {
        return Observable.defer(new Func0<Observable<DocumentFragment<Mutation>>>() {
            @Override
            public Observable<DocumentFragment<Mutation>> call() {
                final SubDeleteRequest request = new SubDeleteRequest(docId, spec.path(), bucketName, expiry, cas);
                request.xattr(spec.xattr());
                addRequestSpan(environment, request, "subdoc_remove");
                return applyTimeout(deferAndWatch(
                    new Func1<Subscriber, Observable<SimpleSubdocResponse>>() {
                        @Override
                        public Observable<SimpleSubdocResponse> call(Subscriber s) {
                            request.subscriber(s);
                            return core.send(request);
                        }
                    })
                    .map(new Func1<SimpleSubdocResponse, DocumentFragment<Mutation>>() {
                        @Override
                        public DocumentFragment<Mutation> call(SimpleSubdocResponse response) {
                            if (response.content() != null && response.content().refCnt() > 0) {
                                response.content().release();
                            }

                            if (environment.tracingEnabled()) {
                                environment.tracer().scopeManager()
                                    .activate(response.request().span(), true)
                                    .close();
                            }
