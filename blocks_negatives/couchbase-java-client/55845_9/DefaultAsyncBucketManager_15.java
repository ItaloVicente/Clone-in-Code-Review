    /**
     * A transformer (to be used with {@link Observable#compose(Observable.Transformer)}) that puts a timeout on an
     * {@link IndexInfo} Observable and will consider {@link IndexesNotReadyException} inside a
     * {@link CannotRetryException} and {@link TimeoutException} to be non-failing cases (resulting in an empty
     * observable), whereas all other errors are propagated as is.
     *
     * @param watchTimeout the timeout to set.
     * @param watchTimeUnit the time unit for the timeout.
     * @param indexName null for a global timeout, or the name of the single index being watched.
     */
    private static Observable.Transformer<IndexInfo, IndexInfo> safeAbort(final long watchTimeout, final TimeUnit watchTimeUnit,
            final String indexName) {
        return new Observable.Transformer<IndexInfo, IndexInfo>() {
            @Override
            public Observable<IndexInfo> call(Observable<IndexInfo> source) {
                return source.timeout(watchTimeout, watchTimeUnit)
                .onErrorResumeNext(new Func1<Throwable, rx.Observable<IndexInfo>>() {
                    @Override
                    public rx.Observable<IndexInfo> call(Throwable t) {
                        if (t instanceof TimeoutException) {
                            if (indexName == null) {
                                INDEX_WATCH_LOG.debug("Watched indexes were not all online after the given {} {}", watchTimeout, watchTimeUnit);
                            } else {
                                INDEX_WATCH_LOG.debug("Index {} was not online after the given {} {}", indexName, watchTimeout, watchTimeUnit);
                            }
                            return Observable.empty();
                        }
                        if (t instanceof CannotRetryException && t.getCause() instanceof IndexesNotReadyException) {
                            INDEX_WATCH_LOG.debug("{} after {} attempts", INDEX_WATCH_MAX_ATTEMPTS, t.getCause().getMessage());
                            return Observable.empty();
                        }
                        return rx.Observable.error(t);
                    }
                });
            }
        };
    }

    /**
     * A transformer (to be used with {@link Observable#compose(Observable.Transformer)}) that takes a N1QL query result
     * and inspects the status and errors.
     *
     * If the query succeeded, emits TRUE. If there is an error but it is only that the index exists, and ignoreIfExist
     * is true, emits FALSE. Otherwise the error is propagated as a CouchbaseException.
     */
    private static Observable.Transformer<AsyncN1qlQueryResult, Boolean> checkIndexCreation(final boolean ignoreIfExist,
            final String prefixMsg) {
        return new Observable.Transformer<AsyncN1qlQueryResult, Boolean>() {
            @Override
            public Observable<Boolean> call(Observable<AsyncN1qlQueryResult> sourceObservable) {
                return sourceObservable
                        .flatMap(new Func1<AsyncN1qlQueryResult, Observable<Boolean>>() {
                            @Override
                            public Observable<Boolean> call(final AsyncN1qlQueryResult aqr) {
                                return aqr.finalSuccess()
                                        .flatMap(new Func1<Boolean, Observable<Boolean>>() {
                                            @Override
                                            public Observable<Boolean> call(Boolean success) {
                                                if (success) {
                                                    return Observable.just(true);
                                                } else {
                                                    return aqr.errors()
                                                    .toList()
                                                    .flatMap(new Func1<List<JsonObject>, Observable<Boolean>>() {
                                                        @Override
                                                        public Observable<Boolean> call(
                                                                List<JsonObject> errors) {
                                                            if (ignoreIfExist && errors.size() == 1
                                                                    && errors.get(0).getString("msg").contains("already exist")) {
                                                                return Observable.just(false);
                                                            } else {
                                                                return Observable.error(new CouchbaseException(prefixMsg + ": " + errors));
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        });
                            }
                        });
            }
        };
    }
