    /**
     * Tests a N1QL error JSON for conditions warranting a prepared statement retry.
     */
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

    /**
     * Peeks into the error stream of the {@link AsyncQueryResult} and reemit a copy of it if no retry condition for
     * prepared statement execution is found, otherwise emit an error than will trigger a retry (see {@link #shouldRetry(JsonObject)}).
     */
    private static final Func1<AsyncQueryResult, Observable<AsyncQueryResult>> QUERY_RESULT_PEEK_FOR_RETRY =
    new Func1<AsyncQueryResult, Observable<AsyncQueryResult>>() {
        @Override
        public Observable<AsyncQueryResult> call(final AsyncQueryResult aqr) {
            final Observable<JsonObject> cachedErrors = aqr.errors().cache();

            return cachedErrors
                    .filter(new Func1<JsonObject, Boolean>() {
                        @Override
                        public Boolean call(JsonObject e) {
                            return shouldRetry(e);
                        }
                    })
                    .lastOrDefault(null)
                    .flatMap(new Func1<JsonObject, Observable<AsyncQueryResult>>() {
                        @Override
                        public Observable<AsyncQueryResult> call(JsonObject errorJson) {
                            if (errorJson == null) {
                                AsyncQueryResult copyResult = new DefaultAsyncQueryResult(
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

