    @Override
    public QueryResult queryRaw(String query, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket
            .queryRaw(query)
            .map(new Func1<AsyncQueryResult, QueryResult>() {
                @Override
                public QueryResult call(AsyncQueryResult asyncQueryResult) {
                    return new DefaultQueryResult(environment, asyncQueryResult.rows(),
                        asyncQueryResult.info(), asyncQueryResult.error(), asyncQueryResult.success());
                }
            })
            .single(), timeout, timeUnit);
    }

