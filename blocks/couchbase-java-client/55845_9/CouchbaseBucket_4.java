    @Override
    public SearchQueryResult query(SearchQuery query) {
        return query(query, environment.searchTimeout(), TIMEOUT_UNIT);
    }

    @Override
    public SearchQueryResult query(SearchQuery query, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket
                .query(query)
                .single(), timeout, timeUnit);
    }

