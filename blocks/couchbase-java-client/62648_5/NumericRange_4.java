package com.couchbase.client.java.fts.facet;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;

public class DateRangeFacet extends SearchFacet {

    private final DateRange[] dateRanges;

    DateRangeFacet(String name, String field, int limit, DateRange[] dateRanges) {
        super(name, field, limit);
        this.dateRanges = dateRanges;
    }

    @Override
    public void injectParams(JsonObject queryJson) {
        super.injectParams(queryJson);

        JsonArray dateRange = JsonArray.empty();
        for (DateRange dr : dateRanges) {
            JsonObject drJson = JsonObject.create();
            drJson.put("name", dr.name());

            if (dr.start() != null) {
                drJson.put("start", dr.start());
            }
            if (dr.end() != null) {
                drJson.put("end", dr.end());
            }

            dateRange.add(drJson);
        }
        queryJson.put("date_ranges", dateRange);
    }
}
