package com.couchbase.client.java.fts.facet;

import java.util.HashMap;
import java.util.Map;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;

public class DateRangeFacet extends SearchFacet {

    private final Map<String, DateRange> dateRanges;

    DateRangeFacet(String name, String field, int limit, String firstRangeName, String firstStart, String firstEnd) {
        super(name, field, limit);
        this.dateRanges = new HashMap<String, DateRange>();

        checkRange(firstRangeName, firstStart, firstEnd);
        this.dateRanges.put(firstRangeName, new DateRange(firstStart, firstEnd));
    }

    public void checkRange(String name, String start, String end) {
        if (name == null) {
            throw new NullPointerException("Cannot create date range without a name");
        }
        if (start == null && end == null) {
            throw new NullPointerException("Cannot create date range without start nor end");
        }
    }

    public DateRangeFacet addRange(String rangeName, String start, String end) {
        checkRange(rangeName, start, end);
        this.dateRanges.put(rangeName, new DateRange(start, end));
        return this;
    }

    @Override
    public void injectParams(JsonObject queryJson) {
        super.injectParams(queryJson);

        JsonArray dateRange = JsonArray.empty();
        for (Map.Entry<String, DateRange> dr : dateRanges.entrySet()) {
            JsonObject drJson = JsonObject.create();
            drJson.put("name", dr.getKey());

            if (dr.getValue().start != null) {
                drJson.put("start", dr.getValue().start);
            }
            if (dr.getValue().end != null) {
                drJson.put("end", dr.getValue().end);
            }

            dateRange.add(drJson);
        }
        queryJson.put("date_ranges", dateRange);
    }

    private static class DateRange {

        public final String start;
        public final String end;

        public DateRange(String start, String end) {
            this.start = start;
            this.end = end;
        }
    }
}
