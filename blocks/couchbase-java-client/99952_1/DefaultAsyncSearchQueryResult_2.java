    public static AsyncSearchQueryResult fromHttp429(String payload) {
        SearchStatus status = new DefaultSearchStatus(1L, 1L, 0L);
        SearchMetrics metrics = new DefaultSearchMetrics(0L, 0L, 0d);

        return new DefaultAsyncSearchQueryResult(
                status,
                Observable.<SearchQueryRow>error(new FtsServerOverloadException(payload)),
                Observable.<FacetResult>empty(),
                Observable.just(metrics)
        );
    }

