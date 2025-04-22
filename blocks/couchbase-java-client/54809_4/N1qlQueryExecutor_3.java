    private static boolean shouldRetry(JsonObject errorJson) {
        if (errorJson == null) return false;
        Integer code = errorJson.getInt(ERROR_FIELD_CODE);
        String msg = errorJson.getString(ERROR_FIELD_MSG);

        if (code == null || msg == null) return false;

        if (code == 4050 || code == 4070 ||
                (code == 5000 && msg.contains(ERROR_5000_SPECIFIC_MESSAGE))) {
            return true;
        }
        return false;
    }

    private static final Func1<AsyncN1qlQueryResult, Observable<AsyncN1qlQueryResult>> QUERY_RESULT_PEEK_FOR_RETRY =
    new Func1<AsyncN1qlQueryResult, Observable<AsyncN1qlQueryResult>>() {
        @Override
        public Observable<AsyncN1qlQueryResult> call(final AsyncN1qlQueryResult aqr) {
            final Observable<JsonObject> cachedErrors = aqr.errors().cache();

            return cachedErrors
                    .filter(new Func1<JsonObject, Boolean>() {
                        @Override
                        public Boolean call(JsonObject e) {
                            return shouldRetry(e);
                        }
                    })
                    .lastOrDefault(null)
                    .flatMap(new Func1<JsonObject, Observable<AsyncN1qlQueryResult>>() {
                        @Override
                        public Observable<AsyncN1qlQueryResult> call(JsonObject errorJson) {
                            if (errorJson == null) {
                                AsyncN1qlQueryResult copyResult = new DefaultAsyncN1qlQueryResult(
                                        aqr.rows(), aqr.signature(), aqr.info(),
                                        cachedErrors,
                                        aqr.finalSuccess(), aqr.parseSuccess(), aqr.requestId(),
                                        aqr.clientContextId());
                                return Observable.just(copyResult);
                            } else {
                                return Observable.error(new QueryExecutionException("Error with prepared query",
                                        errorJson));
                            }
                        }
                    });
        }
    };

    protected Observable<AsyncN1qlQueryResult> dispatchPrepared(final N1qlQuery query) {
        PreparedPayload payload = queryCache.get(query.statement().toString());
        Func1<Throwable, Observable<AsyncN1qlQueryResult>> retryFunction = new Func1<Throwable, Observable<AsyncN1qlQueryResult>>() {
            @Override
            public Observable<AsyncN1qlQueryResult> call(Throwable throwable) {
                return retryPrepareAndExecuteOnce(throwable, query);
            }
        };

        if (payload != null) {
            return executePrepared(query, payload)
                    .flatMap(QUERY_RESULT_PEEK_FOR_RETRY)
                    .onErrorResumeNext(retryFunction);
        } else {
            return prepareAndExecute(query)
                .flatMap(QUERY_RESULT_PEEK_FOR_RETRY)
                .onErrorResumeNext(retryFunction);
        }
    }

    protected Observable<AsyncN1qlQueryResult> retryPrepareAndExecuteOnce(Throwable error, N1qlQuery query) {
        if (error instanceof QueryExecutionException &&
                shouldRetry(((QueryExecutionException) error).getN1qlError())) {
            return prepareAndExecute(query);
        }
        return Observable.error(error);
    }

    protected Observable<AsyncN1qlQueryResult> prepareAndExecute(final N1qlQuery query) {
        return prepare(query.statement())
                .flatMap(new Func1<PreparedPayload, Observable<AsyncN1qlQueryResult>>() {
                    @Override
                    public Observable<AsyncN1qlQueryResult> call(PreparedPayload payload) {
                        queryCache.put(query.statement().toString(), payload);
                        return executePrepared(query, payload);
                    }
                });
    }

    protected Observable<AsyncN1qlQueryResult> executePrepared(final N1qlQuery query, PreparedPayload payload) {
        if (query instanceof ParameterizedN1qlQuery) {
            ParameterizedN1qlQuery pq = (ParameterizedN1qlQuery) query;
            if (pq.isPositional()) {
                return executeQuery(
                    new PreparedN1qlQuery(payload, (JsonArray) pq.statementParameters(), query.params())
                );
            } else {
                return executeQuery(
                    new PreparedN1qlQuery(payload, (JsonObject) pq.statementParameters(), query.params())
                );
            }
        } else {
            return executeQuery(new PreparedN1qlQuery(payload, query.params()));
        }
    }

