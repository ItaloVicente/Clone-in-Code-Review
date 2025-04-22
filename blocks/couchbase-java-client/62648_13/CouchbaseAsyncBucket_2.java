    public Observable<AsyncSearchQueryResult> query(final String indexName, final SearchQuery query) {
        return query(indexName, query, SearchParams.build());
    }

    @Override
    public Observable<AsyncSearchQueryResult> query(final String indexName, final SearchQuery query, final SearchParams params) {
        if (params.getServerSideTimeout() == null) {
            params.serverSideTimeout(environment().searchTimeout(), TimeUnit.MILLISECONDS);
        }

