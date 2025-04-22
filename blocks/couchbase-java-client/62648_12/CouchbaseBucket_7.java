    public SearchQueryResult query(String indexName, SearchQuery query, SearchParams params) {
        return query(indexName, query, params, environment.searchTimeout(), TIMEOUT_UNIT);
    }

    @Override
    public SearchQueryResult query(String indexName, SearchQuery query, long timeout, TimeUnit timeUnit) {
        return query(indexName, query, SearchParams.build(), timeout, timeUnit);
    }

    @Override
    public SearchQueryResult query(String indexName, SearchQuery query, SearchParams params, long timeout, TimeUnit timeUnit) {
