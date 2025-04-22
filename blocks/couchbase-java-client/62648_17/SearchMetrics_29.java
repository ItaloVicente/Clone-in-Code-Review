package com.couchbase.client.java.fts.result;

import com.couchbase.client.java.fts.result.facets.FacetResult;
import rx.Observable;

public interface AsyncSearchQueryResult {

    SearchStatus status();
    Observable<SearchQueryRow> hits();
    Observable<FacetResult> facets();
    Observable<SearchMetrics> metrics();
}
