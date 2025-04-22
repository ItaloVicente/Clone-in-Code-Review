package com.couchbase.client.java.fts.result.facets;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.fts.facet.SearchFacet;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public interface FacetResult {

    String name();

    String field();

    long total();

    long missing();

    long other();
}
