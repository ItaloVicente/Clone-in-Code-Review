package com.couchbase.client.java.fts.result.impl;

import com.couchbase.client.java.fts.result.AsyncSearchQueryResult;
import com.couchbase.client.java.fts.result.SearchMetrics;
import com.couchbase.client.java.fts.result.SearchQueryRow;
import com.couchbase.client.java.fts.result.SearchStatus;
import com.couchbase.client.java.fts.result.facets.FacetResult;
import rx.Observable;

public class DefaultAsyncSearchQueryResult implements AsyncSearchQueryResult {

    private final SearchStatus status;
    private final Observable<SearchQueryRow> hits;
    private final Observable<FacetResult> facets;
    private final Observable<SearchMetrics> metrics;

    public DefaultAsyncSearchQueryResult(SearchStatus status,
            Observable<SearchQueryRow> hits, Observable<FacetResult> facets,
            Observable<SearchMetrics> metrics) {
        this.status = status;
        this.hits = hits;
        this.facets = facets;
        this.metrics = metrics;
    }

    @Override
    public SearchStatus status() {
        return status;
    }

    @Override
    public Observable<SearchQueryRow> hits() {
        return hits;
    }

    @Override
    public Observable<FacetResult> facets() {
        return facets;
    }

    @Override
    public Observable<SearchMetrics> metrics() {
        return metrics;
    }
}
