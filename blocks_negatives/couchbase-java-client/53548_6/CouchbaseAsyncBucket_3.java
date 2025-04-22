    /* package */ Observable<AsyncQueryResult> queryPrepared(final PreparedQuery query) {
        final GenericQueryRequest request = GenericQueryRequest.jsonQuery(query.n1ql().toString(), bucket, password);

        return queryRaw(query.n1ql().toString())
        .flatMap(new Func1<AsyncQueryResult, Observable<AsyncQueryResult>>() {
            @Override
            public Observable<AsyncQueryResult> call(final AsyncQueryResult aqr) {
                final Observable<AsyncQueryRow> cachedRows = aqr.rows().cache();
                return cachedRows
                        .firstOrDefault(null)
                        .flatMap(new Func1<AsyncQueryRow, Observable<AsyncQueryResult>>() {
                            @Override
                            public Observable<AsyncQueryResult> call(AsyncQueryRow row) {
                                if (row != null && row.value().containsKey("operator")
                                        && row.value().getObject("operator").containsKey("#operator")) {
                                    return Observable.error(
                                            new NamedPreparedStatementException("Named prepared statement "
                                                    + query.statement().preparedName()
                                                    + " not found, it has been re-prepared"));
                                }

                                AsyncQueryResult copyResult = new DefaultAsyncQueryResult(cachedRows,
                                        aqr.signature(), aqr.info(), aqr.errors(), aqr.finalSuccess(),
                                        aqr.parseSuccess(), aqr.requestId(), aqr.clientContextId());

                                return Observable.just(copyResult);
                            }
                        });
            }
        })
        .retry(new Func2<Integer, Throwable, Boolean>() {
            @Override
            public Boolean call(Integer attempts, Throwable throwable) {
                if (throwable instanceof NamedPreparedStatementException
                        && (attempts == 1 || environment.retryStrategy().shouldRetry(request, environment()))){
                    LOGGER.warn("Retrying #" + attempts + " prepared query " + query.n1ql());
                    return true;
                }
                return false;
            }
        });
