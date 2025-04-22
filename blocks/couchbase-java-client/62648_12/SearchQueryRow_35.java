package com.couchbase.client.java.fts.result;

import java.util.List;
import java.util.Map;

import com.couchbase.client.java.fts.result.facets.FacetResult;

public interface SearchQueryResult {

    SearchStatus status();

    List<SearchQueryRow> hits();
    List<SearchQueryRow> hitsOrFail();
    List<String> errors();
    Map<String, FacetResult> facets();

    SearchMetrics metrics();
}
