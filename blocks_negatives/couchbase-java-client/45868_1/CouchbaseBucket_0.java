        return Blocking.blockForSingle(asyncBucket
            .query(statement)
            .map(new Func1<AsyncQueryResult, QueryResult>() {
                @Override
                public QueryResult call(AsyncQueryResult asyncQueryResult) {
                    return new DefaultQueryResult(asyncQueryResult.rows(), asyncQueryResult.info(),
                            asyncQueryResult.errors(), asyncQueryResult.finalSuccess(), asyncQueryResult.parseSuccess(),
                            asyncQueryResult.requestId(), asyncQueryResult.clientContextId(),
                            timeout, timeUnit);
                }
            })
            .single(), timeout, timeUnit);
