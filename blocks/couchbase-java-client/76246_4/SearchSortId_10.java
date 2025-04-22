package com.couchbase.client.java.search.sort;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;

public class SearchSortGeo extends SearchSort {

    private final String field;
    private final double locationLon;
    private final double locationLat;
    private String unit;

    public SearchSortGeo(double locationLon, double locationLat, String field) {
        this.field = field;
        this.locationLon = locationLon;
        this.locationLat = locationLat;
    }

    @Override
    protected String identifier() {
        return "geo";
    }

    @Override
    public SearchSortGeo descending(boolean descending) {
        super.descending(descending);
        return this;
    }

    public SearchSortGeo unit(String unit) {
        this.unit = unit;
        return this;
    }

    @Override
    public void injectParams(JsonObject queryJson) {
        super.injectParams(queryJson);

        queryJson.put("location", JsonArray.from(locationLon, locationLat));
        queryJson.put("field", field);

        if (unit != null) {
            queryJson.put("unit", unit);
        }
    }
}
