    @Override
    public QueryResult query(Statement statement, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket
            .query(statement)
            .map(new Func1<AsyncQueryResult, QueryResult>() {
                @Override
                public QueryResult call(AsyncQueryResult asyncQueryResult) {
                    return new DefaultQueryResult(environment, asyncQueryResult.rows(),
                        asyncQueryResult.info(), asyncQueryResult.error(), asyncQueryResult.success());
                }
            })
            .single(), timeout, timeUnit);
    }

