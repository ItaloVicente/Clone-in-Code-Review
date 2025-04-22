    @Override
    public SearchQueryResult search(String index, String query) {
        return search(SearchQuery.builder().index(index).query(query).build(), environment.searchTimeout(), TIMEOUT_UNIT);
    }

    @Override
    public SearchQueryResult search(String index, String query, long timeout, TimeUnit timeUnit) {
        return search(SearchQuery.builder().index(index).query(query).build(), timeout, timeUnit);
    }

    @Override
    public SearchQueryResult search(SearchQuery query) {
        return search(query, environment.searchTimeout(), TIMEOUT_UNIT);
    }

    @Override
    public SearchQueryResult search(SearchQuery query, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket
                .search(query)
                .single(), timeout, timeUnit);
    }

