package com.couchbase.client.java.fts.result.facets;

import java.util.List;

public interface DateRangeFacetResult extends FacetResult {

    List<DateRange> dateRanges();
}
