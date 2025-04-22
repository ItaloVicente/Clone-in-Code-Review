package com.couchbase.client.java.fts.result.facets;

public class TermRange {

    private final String name;
    private final long count;

    public TermRange(String name, long count) {
        this.name = name;
        this.count = count;
    }

    public String name() {
        return name;
    }

    public long count() {
        return count;
    }
}
