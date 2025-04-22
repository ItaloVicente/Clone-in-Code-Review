package com.couchbase.client.java.search.queries;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonObject;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class TermRangeQuery extends AbstractFtsQuery {

    private String min;
    private String max;
    private Boolean inclusiveMin = null;
    private Boolean inclusiveMax = null;
    private String field;

    public TermRangeQuery() {
        super();
    }

    public TermRangeQuery min(String min, boolean inclusive) {
        this.min = min;
        this.inclusiveMin = inclusive;
        return this;
    }

    public TermRangeQuery min(String min) {
        this.min = min;
        this.inclusiveMin = null;
        return this;
    }

    public TermRangeQuery max(String max, boolean inclusive) {
        this.max = max;
        this.inclusiveMax = inclusive;
        return this;
    }

    public TermRangeQuery max(String max) {
        this.max = max;
        this.inclusiveMax = null;
        return this;
    }

    public TermRangeQuery field(String field) {
        this.field = field;
        return this;
    }

    @Override
    public TermRangeQuery boost(double boost) {
        super.boost(boost);
        return this;
    }

    @Override
    protected void injectParams(JsonObject input) {
        if (min == null && max == null) {
            throw new NullPointerException("TermRangeQuery needs at least one of min or max");
        }
        if (min != null) {
            input.put("min", min);
            if (inclusiveMin != null) {
                input.put("inclusive_min", inclusiveMin);
            }
        }
        if (max != null) {
            input.put("max", max);
            if (inclusiveMax != null) {
                input.put("inclusive_max", inclusiveMax);
            }
        }
        if (field != null) {
            input.put("field", field);
        }
    }
}
