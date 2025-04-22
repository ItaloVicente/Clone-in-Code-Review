package com.couchbase.client.java.fts.queries;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.fts.SearchParams;
import com.couchbase.client.java.fts.SearchQuery;

public class NumericRangeQuery extends SearchQuery {

    private Double min;
    private Double max;
    private boolean inclusiveMin = true;
    private boolean inclusiveMax = false;
    private String field;

    public NumericRangeQuery() {
        super();
    }

    public NumericRangeQuery min(double min, boolean inclusive) {
        this.min = min;
        this.inclusiveMin = inclusive;
        return this;
    }

    public NumericRangeQuery min(double min) {
        return min(min, true);
    }

    public NumericRangeQuery max(double max, boolean inclusive) {
        this.max = max;
        this.inclusiveMax = inclusive;
        return this;
    }

    public NumericRangeQuery max(double max) {
        return max(max, false);
    }

    public NumericRangeQuery field(String field) {
        this.field = field;
        return this;
    }

    @Override
    public NumericRangeQuery boost(double boost) {
        super.boost(boost);
        return this;
    }

    @Override
    protected void injectParams(JsonObject input) {
        if (min == null && max == null) {
            throw new NullPointerException("NumericRangeQuery needs at least one of min or max");
        }
        if (min != null) {
            input.put("min", min);
            input.put("inclusive_min", inclusiveMin);
        }
        if (max != null) {
            input.put("max", max);
            input.put("inclusive_max", inclusiveMax);
        }
        if (field != null) {
            input.put("field", field);
        }
    }
}
