    private static <T> Func1<List<JsonObject>, Observable<T>> errorsToThrowable(final String messagePrefix) {
        return new Func1<List<JsonObject>, Observable<T>>() {
            @Override
            public Observable<T> call(List<JsonObject> errors) {
                return Observable.<T>error(new CouchbaseException(messagePrefix + errors));
            }
        };
    }

    private static Expression expressionOrIdentifier(Object o) {
        if (o instanceof Expression) {
            return (Expression) o;
        } else if (o instanceof String) {
            return i((String) o);
        } else {
            throw new IllegalArgumentException("Fields for index must be either an Expression or a String identifier");
        }
    }

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

