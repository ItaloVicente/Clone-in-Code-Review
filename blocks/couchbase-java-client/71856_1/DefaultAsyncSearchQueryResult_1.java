
    @Deprecated
    public static AsyncSearchQueryResult fromIndexNotFound(final String indexName) {
        SearchStatus status = new DefaultSearchStatus(1L, 1L, 0L);
        SearchMetrics metrics = new DefaultSearchMetrics(0L, 0L, 0d);


        return new DefaultAsyncSearchQueryResult(
            status,
            Observable.<SearchQueryRow>error(new IndexDoesNotExistException("Search Index \"" + indexName
                + "\" Not Found")),
            Observable.<FacetResult>empty(),
            Observable.just(metrics)
        );
    }
