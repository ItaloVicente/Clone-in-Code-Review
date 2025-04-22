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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("{");
        sb.append("name='").append(name).append('\'');
        if (start != null) {
            sb.append(", start='").append(start).append('\'');
        }
        if (end != null) {
            sb.append(", end='").append(end).append('\'');
        }
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }
}
