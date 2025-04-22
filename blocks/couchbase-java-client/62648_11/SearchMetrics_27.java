package com.couchbase.client.java.fts.result;

import com.couchbase.client.java.fts.result.facets.FacetResult;
import com.couchbase.client.java.search.SearchQueryRow;
import rx.Observable;

public interface AsyncSearchQueryResult {

    SearchStatus status();
    Observable<SearchQueryRow> hits();
    Observable<FacetResult> facets();
    Observable<SearchMetrics> metrics();
}
