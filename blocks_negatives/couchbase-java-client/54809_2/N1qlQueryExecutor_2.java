    protected Observable<AsyncQueryResult> dispatchPrepared(final Query query) {
        PreparedPayload payload = queryCache.get(query.statement().toString());
        Func1<Throwable, Observable<AsyncQueryResult>> retryFunction = new Func1<Throwable, Observable<AsyncQueryResult>>() {
            @Override
            public Observable<AsyncQueryResult> call(Throwable throwable) {
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

    /**
     * In case the error warrants a retry, issue a PREPARE, followed by an update
     * of the cache and an EXECUTE.
     * Any failure in the EXECUTE won't continue the retry cycle.
     */
    protected Observable<AsyncQueryResult> retryPrepareAndExecuteOnce(Throwable error, Query query) {
        if (error instanceof QueryExecutionException &&
                shouldRetry(((QueryExecutionException) error).getN1qlError())) {
            return prepareAndExecute(query);
        }
        return Observable.error(error);
    }

    /**
     * Issues a N1QL PREPARE, puts the plan in cache then EXECUTE it.
     */
    protected Observable<AsyncQueryResult> prepareAndExecute(final Query query) {
        return prepare(query.statement())
                .flatMap(new Func1<PreparedPayload, Observable<AsyncQueryResult>>() {
                    @Override
                    public Observable<AsyncQueryResult> call(PreparedPayload payload) {
                        queryCache.put(query.statement().toString(), payload);
                        return executePrepared(query, payload);
                    }
                });
    }

    /**
     * Issues a proper N1QL EXECUTE, detecting if parameters must be added to it.
     */
    protected Observable<AsyncQueryResult> executePrepared(final Query query, PreparedPayload payload) {
        if (query instanceof ParameterizedQuery) {
            ParameterizedQuery pq = (ParameterizedQuery) query;
            if (pq.isPositional()) {
                return executeQuery(
                    new PreparedQuery(payload, (JsonArray) pq.statementParameters(), query.params())
                );
            } else {
                return executeQuery(
                    new PreparedQuery(payload, (JsonObject) pq.statementParameters(), query.params())
                );
            }
        } else {
            return executeQuery(new PreparedQuery(payload, query.params()));
        }
    }

