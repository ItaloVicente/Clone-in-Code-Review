                public QueryResult call(AsyncQueryResult asyncQueryResult) {
                    return new DefaultQueryResult(environment, asyncQueryResult.rows(),
                        asyncQueryResult.info(), asyncQueryResult.error(), asyncQueryResult.success());
                }
            })
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
    }
