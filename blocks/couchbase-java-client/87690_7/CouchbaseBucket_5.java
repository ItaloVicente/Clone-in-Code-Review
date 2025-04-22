    @Override
    public N1qlQueryResult query(N1qlQuery query, Span parent) {
        if (!query.params().hasServerSideTimeout()) {
            query.params().serverSideTimeout(environment.queryTimeout(), TIMEOUT_UNIT);
        }

        return Blocking.blockForSingle(asyncBucket
          .query(query, parent)
          .flatMap(N1qlQueryExecutor.ASYNC_RESULT_TO_SYNC)
          .single(), environment.queryTimeout(), TIMEOUT_UNIT);    }

