package com.couchbase.client.java.fts.result.facets;

import java.util.List;

public interface TermFacetResult extends FacetResult {

    List<TermRange> terms();
}
