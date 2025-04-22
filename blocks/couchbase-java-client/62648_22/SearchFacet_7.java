package com.couchbase.client.java.fts.facet;

import java.util.HashMap;
import java.util.Map;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class NumericRangeFacet extends SearchFacet {

    private static class NumericRange {
        public final Double min;
        public final Double max;

        public NumericRange(Double min, Double max) {
            this.min = min;
            this.max = max;
        }
    }

    private final Map<String, NumericRange> numericRanges;

    protected NumericRangeFacet(String name, String field, int limit) {
        super(name, field, limit);
        this.numericRanges = new HashMap<String, NumericRange>();
    }

    protected void checkRange(String name, Double min, Double max) {
        if (name == null) {
            throw new NullPointerException("Cannot create numeric range without a name");
        }
        if (min == null && max == null) {
            throw new NullPointerException("Cannot create numeric range without min nor max");
        }
    }

    public NumericRangeFacet addRange(String name, Double min, Double max) {
        checkRange(name, min, max);
        this.numericRanges.put(name, new NumericRange(min, max));

        return this;
    }

    @Override
    public void injectParams(JsonObject queryJson) {
        super.injectParams(queryJson);

        JsonArray numericRange = JsonArray.empty();
        for (Map.Entry<String, NumericRange> nr : numericRanges.entrySet()) {
            JsonObject nrJson = JsonObject.create();
            nrJson.put("name", nr.getKey());

            if (nr.getValue().min != null) {
                nrJson.put("min", nr.getValue().min);
            }
            if (nr.getValue().max != null) {
                nrJson.put("max", nr.getValue().max);
            }

            numericRange.add(nrJson);
        }
        queryJson.put("numeric_ranges", numericRange);
    }
}
