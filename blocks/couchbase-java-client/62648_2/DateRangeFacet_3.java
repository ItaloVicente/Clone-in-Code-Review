package com.couchbase.client.java.fts.facet;

public class DateRange {

    private final String name;
    private final String start;
    private final String end;

    public DateRange(String name, String start, String end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public static DateRange dr(String name, String start, String end) {
        return new DateRange(name, start, end);
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
}
