    @Override
    public SearchQueryResult search(String index, String query) {
        return search(StringQuery.builder().query(query).index(index).build(), environment.searchTimeout(), TIMEOUT_UNIT);
    }

    @Override
    public SearchQueryResult search(String index, String query, long timeout, TimeUnit timeUnit) {
        return search(StringQuery.builder().query(query).index(index).build(), timeout, timeUnit);
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

