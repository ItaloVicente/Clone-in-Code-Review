
    @Override
    public N1qlQueryResult query(N1qlQuery query) {
        return query(query, environment.queryTimeout(), TIMEOUT_UNIT);
    }

    @Override
    public N1qlQueryResult query(N1qlQuery query, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(
                couchbaseAsyncCluster
                        .query(query)
                        .flatMap(N1qlQueryExecutor.ASYNC_RESULT_TO_SYNC),
                timeout, timeUnit);
    }
