package com.couchbase.client.java.fts.result.facets;

import java.util.List;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.fts.facet.DateRangeFacet;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public interface DateRangeFacetResult extends FacetResult {

    List<DateRange> dateRanges();
}
