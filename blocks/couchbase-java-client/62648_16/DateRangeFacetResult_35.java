package com.couchbase.client.java.fts.result.facets;

import java.util.Date;

import com.couchbase.client.java.fts.util.SearchUtils;

public class DateRange {

    private final String name;
    private final Date start;
    private final Date end;
    private final long count;

    public DateRange(String name, String start, String end, long count) {
        this.name = name;
        this.count = count;

        this.start = SearchUtils.fromFtsString(start);
        this.end = SearchUtils.fromFtsString(end);
    }

    public String name() {
        return name;
    }

    public Date start() {
        return start;
    }

    public Date end() {
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
