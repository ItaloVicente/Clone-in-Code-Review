package com.couchbase.client.java.fts.result.facets;

import java.util.List;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.fts.facet.NumericRangeFacet;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public interface NumericRangeFacetResult extends FacetResult {

    List<NumericRange> numericRanges();
}
