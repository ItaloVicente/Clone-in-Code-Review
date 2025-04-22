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

                            ResponseStatus responseStatus = response.status();
                            if (!responseStatus.isSuccess()) {
                                CouchbaseException subdocError = SubdocHelper.commonSubdocErrors(response.status(), docId, spec.path());
                                if (SubdocHelper.isSubdocLevelError(responseStatus)) {
                                    throw new MultiMutationException(0, responseStatus, Collections.singletonList(spec), subdocError);
                                } else {
                                    throw subdocError;
                                }
                            }

                            SubdocOperationResult<Mutation> singleResult = SubdocOperationResult
