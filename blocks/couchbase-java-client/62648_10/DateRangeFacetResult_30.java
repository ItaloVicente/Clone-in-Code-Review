package com.couchbase.client.java.fts.result.facets;

public class DateRange {

    private final String name;
    private final String start;
    private final String end;
    private final long count;

    public DateRange(String name, String start, String end, long count) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.count = count;
    }

    public String name() {
        return name;
    }

    public String start() {
        return start;
    }

    public String end() {
        return end;
    }

    public long count() {
        return count;
    }
}
