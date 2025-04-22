    public Observable<AsyncAnalyticsQueryResult> query(final AnalyticsQuery query) {
        if (!query.params().hasServerSideTimeout()) {
            query.params().serverSideTimeout(environment().queryTimeout(), TimeUnit.MILLISECONDS);
        }
        return analyticsQueryExecutor.execute(query);
    }

