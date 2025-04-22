package com.couchbase.client.java.fts.queries;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.fts.SearchParams;
import com.couchbase.client.java.fts.SearchQuery;

public class DateRangeQuery extends SearchQuery {

    private String start;
    private String  end;
    private boolean inclusiveStart = true;
    private boolean inclusiveEnd = false;
    private String dateTimeParser;
    private String field;

    public DateRangeQuery() {
        super();
    }

    public DateRangeQuery start(String start, boolean inclusive) {
        this.start = start;
        this.inclusiveStart = inclusive;
        return this;
    }

    public DateRangeQuery start(String start) {
        return start(start, true);
    }

    public DateRangeQuery end(String end, boolean inclusive) {
        this.end = end;
        this.inclusiveEnd = inclusive;
        return this;
    }

    public DateRangeQuery end(String end) {
        return end(end, false);
    }

    public DateRangeQuery dateTimeParser(String dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
        return this;
    }

    public DateRangeQuery field(String field) {
        this.field = field;
        return this;
    }

    @Override
    public DateRangeQuery boost(double boost) {
        super.boost(boost);
        return this;
    }

    @Override
    protected void injectParams(JsonObject input) {
        if (start == null && end == null) {
            throw new NullPointerException("DateRangeQuery needs at least one of start or end");
        }

        if (start != null) {
            input.put("start", start);
            input.put("inclusive_start", inclusiveStart);
        }
        if (end != null) {
            input.put("end", end);
            input.put("inclusive_end", inclusiveEnd);
        }
        if (dateTimeParser != null) {
            input.put("datetime_parser", dateTimeParser);
        }
        if (field != null) {
            input.put("field", field);
        }
    }
}
