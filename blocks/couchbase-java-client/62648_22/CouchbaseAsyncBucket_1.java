    public Observable<AsyncSearchQueryResult> query(SearchQuery query) {
        final String indexName = query.indexName();
        final AbstractFtsQuery queryPart = query.query();
        final SearchParams params = query.params();

        if (params.getServerSideTimeout() == null) {
            params.serverSideTimeout(environment().searchTimeout(), TimeUnit.MILLISECONDS);
        }

