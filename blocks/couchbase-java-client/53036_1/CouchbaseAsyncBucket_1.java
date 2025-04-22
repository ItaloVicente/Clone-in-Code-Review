        Observable<AsyncQueryResult> result = queryRaw(query.n1ql().toString());

        if (query instanceof PreparedQuery && environment().retryStrategy() == BestEffortRetryStrategy.INSTANCE) {
            retry handling for prepared statements:
             - the statement() is a PreparedPayload (with a name and the original statement)
             - attempting to prepare a PreparedPayload will reuse the name and original statement
             - this will update the query node's cache with the same info as the node on which we previously prepared
            result = result.flatMap(new Func1<AsyncQueryResult, Observable<? extends AsyncQueryResult>>() {
                @Override
                public Observable<? extends AsyncQueryResult> call(AsyncQueryResult aqr) {
                    Observable<JsonObject> errors = aqr.errors().cache();
                    return errors
                            .filter(new Func1<JsonObject, Boolean>() {
                                @Override
                                public Boolean call(JsonObject error) {
                                    return error.getInt("code") == 4040;
                                }
                            })
                            .doOnNext(new Action1<JsonObject>() {
                                @Override
                                public void call(JsonObject planNotFound) {
                                    LOGGER.warn("Re-prepared a named prepared statement, " + planNotFound.getString("msg"));
                                }
                            })
                            .flatMap(new Func1<JsonObject, Observable<PreparedPayload>>() {
                                @Override
                                public Observable<PreparedPayload> call(JsonObject planNotFound) {
                                    return CouchbaseAsyncBucket.this.prepare(query.statement());
                                }
                            })
                            .flatMap(new Func1<PreparedPayload, Observable<AsyncQueryResult>>() {
                                @Override
                                public Observable<AsyncQueryResult> call(PreparedPayload newPlan) {
                                    return queryRaw(query.n1ql().toString());
                                }
                            })
                            .defaultIfEmpty(new DefaultAsyncQueryResult(aqr.rows(), aqr.signature(),
                                    aqr.info(), errors, aqr.finalSuccess(), aqr.parseSuccess(),
                                    aqr.requestId(), aqr.clientContextId()));
                }
            });

        }
        return result;
