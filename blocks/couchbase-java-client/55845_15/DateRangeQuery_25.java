
package com.couchbase.client.java.search.query;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@InterfaceAudience.Public
@InterfaceStability.Experimental
public class DateRangeQuery extends SearchQuery {
    private static final boolean INCLUSIVE_START = true;
    private static final boolean INCLUSIVE_END = false;
    private static final String DATE_TIME_PARSER = "dateTimeOptional"; // also "goflexible"
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    private final Date start;
    private final Date end;
    private final boolean inclusiveStart;
    private final boolean inclusiveEnd;
    private final String field;
    private final String dateTimeParser;

    protected DateRangeQuery(Builder builder) {
        super(builder);
        start = builder.start;
        end = builder.end;
        inclusiveStart = builder.inclusiveStart;
        inclusiveEnd = builder.inclusiveEnd;
        field = builder.field;
        dateTimeParser = builder.dateTimeParser;
    }

    public static Builder on(String index) {
        return new Builder(index);
    }

    public Date start() {
        return start;
    }

    public Date end() {
        return end;
    }

    public boolean inclusiveStart() {
        return inclusiveStart;
    }

    public boolean inclusiveEnd() {
        return inclusiveEnd;
    }

    public String field() {
        return field;
    }

    public double boost() {
        return boost;
    }

    @Override
    public JsonObject queryJson() {
        return JsonObject.create()
                .put("start", DATE_FORMAT.format(start))
                .put("end", DATE_FORMAT.format(end))
                .put("inclusiveStart", inclusiveStart)
                .put("inclusiveEnd", inclusiveEnd)
                .put("field", field)
                .put("datetime_parser", dateTimeParser);
    }

    public static class Builder extends SearchQuery.Builder {
        public String dateTimeParser = DATE_TIME_PARSER;
        private Date start;
        private Date end;
        private boolean inclusiveStart = INCLUSIVE_START;
        private boolean inclusiveEnd = INCLUSIVE_END;
        private String field;

        protected Builder(String index) {
            super(index);
        }

        public DateRangeQuery build() {
            return new DateRangeQuery(this);
        }

        public Builder start(Date start) {
            this.start = start;
            return this;
        }

        public Builder end(Date end) {
            this.end = end;
            return this;
        }

        public Builder inclusiveStart(boolean inclusiveStart) {
            this.inclusiveStart = inclusiveStart;
            return this;
        }

        public Builder inclusiveEnd(boolean inclusiveEnd) {
            this.inclusiveEnd = inclusiveEnd;
            return this;
        }

        public Builder field(String field) {
            this.field = field;
            return this;
        }

        public Builder dateTimeParser(String dateTimeParser) {
            this.dateTimeParser = dateTimeParser;
            return this;
        }
    }
}
