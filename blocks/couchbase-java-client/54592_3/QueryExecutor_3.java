            return prepareAndExecute(query)
                .flatMap(QUERY_RESULT_PEEK_FOR_RETRY)
                .onErrorResumeNext(retryFunction);
        }
    }

    protected Observable<AsyncQueryResult> retryPrepareAndExecuteOnce(Throwable error, Query query) {
        if (error instanceof QueryExecutionException &&
                shouldRetry(((QueryExecutionException) error).getN1qlError())) {
            return prepareAndExecute(query);
        }
        return Observable.error(error);
    }

    protected Observable<AsyncQueryResult> prepareAndExecute(final Query query) {
        return prepare(query.statement())
