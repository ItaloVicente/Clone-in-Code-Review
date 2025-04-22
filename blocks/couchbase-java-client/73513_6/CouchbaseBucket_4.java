    @Override
    public AnalyticsQueryResult query(AnalyticsQuery query) {
        return query(query, environment.analyticsTimeout(), TIMEOUT_UNIT);
    }

    @Override
    public AnalyticsQueryResult query(AnalyticsQuery query, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket
            .query(query)
            .flatMap(AnalyticsQueryExecutor.ASYNC_RESULT_TO_SYNC)
            .single(), timeout, timeUnit);
    }

