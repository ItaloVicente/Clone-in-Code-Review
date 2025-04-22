    @Override
    public SearchQueryResult search(String index, String query) {
        return search(StringQuery.on(index).query(query).build(), environment.searchTimeout(), TIMEOUT_UNIT);
    }

    @Override
    public SearchQueryResult search(String index, String query, long timeout, TimeUnit timeUnit) {
        return search(StringQuery.on(index).query(query).build(), timeout, timeUnit);
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

    @Override
    public SearchQueryResult search(SearchQuery.Builder query) {
        return search(query.build());
    }

    @Override
    public SearchQueryResult search(SearchQuery.Builder query, long timeout, TimeUnit timeUnit) {
        return search(query.build(), timeout, timeUnit);
    }

