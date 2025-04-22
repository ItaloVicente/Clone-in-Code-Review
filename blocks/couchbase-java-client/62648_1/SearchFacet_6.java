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
            numericRange.add(JsonObject.create()
                .put("name", nr.name())
                .put("min", nr.min())
                .put("max", nr.max())
            );
        }
        queryJson.put("numeric_ranges", numericRange);
    }
}
