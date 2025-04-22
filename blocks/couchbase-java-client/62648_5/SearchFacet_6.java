package com.couchbase.client.java.fts.facet;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;

public class NumericRangeFacet extends SearchFacet {

    private final NumericRange[] numericRanges;

    NumericRangeFacet(String name, String field, int limit, NumericRange[] numericRanges) {
        super(name, field, limit);
        this.numericRanges = numericRanges;
    }

    @Override
    public void injectParams(JsonObject queryJson) {
        super.injectParams(queryJson);

        JsonArray numericRange = JsonArray.empty();
        for (NumericRange nr : numericRanges) {
            JsonObject nrJson = JsonObject.create();
            nrJson.put("name", nr.name());

            if (nr.min() != null) {
                nrJson.put("min", nr.min());
            }
            if (nr.max() != null) {
                nrJson.put("max", nr.max());
            }

            numericRange.add(nrJson);
        }
        queryJson.put("numeric_ranges", numericRange);
    }
}
