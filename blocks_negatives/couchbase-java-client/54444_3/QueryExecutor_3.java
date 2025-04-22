
    /**
     * Experimental, Internal: Execute a prepared query, without retry behavior.
     *
     * The returned {@link Observable} can error under the following conditions:
     *
     * - The prepared statement's name was not known on the server and it was re-prepared:
     * {@link NamedPreparedStatementException}
     * - The producer outpaces the SDK: {@link BackpressureException}
     * - The operation had to be cancelled while "in flight" on the wire: {@link RequestCancelledException}
     *
     * @param query the prepared query (including parameters).
     * @return a result similar to {@link #executeRaw(String)} if the prepared query could be executed,
     * or similar to {@link #prepare(Statement)} )} if it had to be re-prepared on the node.
     */
    public Observable<AsyncQueryResult> executePrepared(final PreparedQuery query) {
        return executeRaw(query.n1ql().toString())
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
                });
    }

    /**
     * Experimental, Internal: Execute a prepared query, with retry behavior (at least one retry, maybe more depending
     * on the {@link RetryStrategy}) instead of throwing a {@link NamedPreparedStatementException} immediately.
     *
     * The returned {@link Observable} can error under the following conditions:
     *
     * - The prepared statement's name was not known on the server and it was re-prepared:
     * {@link NamedPreparedStatementException}
     * - The producer outpaces the SDK: {@link BackpressureException}
     * - The operation had to be cancelled while "in flight" on the wire: {@link RequestCancelledException}
     *
     * @param query the prepared query (including parameters).
     * @return a result similar to {@link #executeRaw(String)} if the prepared query could be executed,
     * or similar to {@link #prepare(Statement)} )} if it had to be re-prepared on the node.
     */
    public Observable<AsyncQueryResult> executePreparedWithRetry(final PreparedQuery query) {
        final GenericQueryRequest request = GenericQueryRequest.jsonQuery(query.n1ql().toString(), bucket, password);

        return executePrepared(query)
                .retry(new Func2<Integer, Throwable, Boolean>() {
                    @Override
                    public Boolean call(Integer attempts, Throwable throwable) {
                        if (throwable instanceof NamedPreparedStatementException
                                && (attempts == 1 || environment.retryStrategy().shouldRetry(request, environment))) {
                            LOGGER.warn("Retrying #" + attempts + " prepared query " + query.n1ql());
                            return true;
                        }
                        return false;
                    }
                });
    }
