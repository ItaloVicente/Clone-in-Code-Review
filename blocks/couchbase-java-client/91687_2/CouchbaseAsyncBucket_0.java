    public Observable<AsyncSearchQueryResult> query(SearchQuery query) {
        return query(query, environment.searchTimeout(), TimeUnit.MILLISECONDS);
    }

    @Override
    public Observable<AsyncSearchQueryResult> query(final SearchQuery query, final long timeout, final TimeUnit timeUnit) {
