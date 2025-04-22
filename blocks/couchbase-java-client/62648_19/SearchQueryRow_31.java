package com.couchbase.client.java.fts.result;

import java.util.List;
import java.util.Map;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.fts.SearchParams;
import com.couchbase.client.java.fts.facet.SearchFacet;
import com.couchbase.client.java.fts.result.facets.FacetResult;
import rx.exceptions.CompositeException;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public interface SearchQueryResult {

    SearchStatus status();

    List<SearchQueryRow> hits();

    List<SearchQueryRow> hitsOrFail();

    List<String> errors();

    Map<String, FacetResult> facets();

    SearchMetrics metrics();
}
