package com.couchbase.client.java.fts.queries;

import java.util.Date;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.fts.util.SearchUtils;

public class DateRangeQuery extends AbstractFtsQuery {

    private String start;
    private String  end;
    private Boolean inclusiveStart = null;
    private Boolean inclusiveEnd = null;
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
        this.start = start;
        this.inclusiveStart = null;
        return this;
    }

    public DateRangeQuery end(String end, boolean inclusive) {
        this.end = end;
        this.inclusiveEnd = inclusive;
        return this;
    }

    public DateRangeQuery end(String end) {
        this.end = end;
        this.inclusiveEnd = null;
        return this;
    }


    public DateRangeQuery start(Date start, boolean inclusive) {
        this.start = SearchUtils.toFtsUtcString(start);
        this.inclusiveStart = inclusive;
        return this;
    }

    public DateRangeQuery start(Date start) {
        this.start = SearchUtils.toFtsUtcString(start);
        this.inclusiveStart = null;
        return this;
    }

    public DateRangeQuery end(Date end, boolean inclusive) {
        this.end = SearchUtils.toFtsUtcString(end);
        this.inclusiveEnd = inclusive;
        return this;
    }

    public DateRangeQuery end(Date end) {
        this.end = SearchUtils.toFtsUtcString(end);
        this.inclusiveEnd = null;
        return this;
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
            if (inclusiveStart != null) {
                input.put("inclusive_start", inclusiveStart);
            }
        }
        if (end != null) {
            input.put("end", end);
            if (inclusiveEnd != null) {
                input.put("inclusive_end", inclusiveEnd);
            }
        }
        if (dateTimeParser != null) {
            input.put("datetime_parser", dateTimeParser);
        }
        if (field != null) {
            input.put("field", field);
        }
    }
}
