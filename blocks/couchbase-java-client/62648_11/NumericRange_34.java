package com.couchbase.client.java.fts.result.facets;

public interface FacetResult {

    String name();
    String field();
    long total();
    long missing();
    long other();
}
