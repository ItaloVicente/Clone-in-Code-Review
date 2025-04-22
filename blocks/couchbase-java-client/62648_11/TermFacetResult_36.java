package com.couchbase.client.java.fts.result.facets;

import java.util.List;

public interface NumericRangeFacetResult extends FacetResult {

    List<NumericRange> numericRanges();
}
