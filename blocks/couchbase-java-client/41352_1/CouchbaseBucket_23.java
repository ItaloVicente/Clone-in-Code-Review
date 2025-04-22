    @Override
    public QueryResult query(String query, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .query(query)
            .map(new Func1<AsyncQueryResult, QueryResult>() {
                @Override
                public QueryResult call(AsyncQueryResult asyncQueryResult) {
                    return new DefaultQueryResult(environment, asyncQueryResult.rows(),
                        asyncQueryResult.info(), asyncQueryResult.error(), asyncQueryResult.success());
