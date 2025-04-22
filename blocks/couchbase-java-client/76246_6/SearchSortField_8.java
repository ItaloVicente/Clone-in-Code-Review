package com.couchbase.client.java.search.sort;

import com.couchbase.client.java.document.json.JsonObject;

public abstract class SearchSort {

    private boolean descending;

    protected SearchSort() {
        this.descending = false;
    }

    protected abstract String identifier();

    public void injectParams(final JsonObject queryJson) {
        queryJson.put("by", identifier());
        if (descending) {
            queryJson.put("descending", descending);
        }
    }

    public SearchSort descending(boolean descending) {
        this.descending = descending;
        return this;
    }

    public static SearchSortId sortId() {
        return new SearchSortId();
    }

    public static SearchSortScore sortScore() {
        return new SearchSortScore();
    }

    public static SearchSortField sortField(String field) {
        return new SearchSortField(field);
    }

    public static SearchSortGeo sortGeo(double locationLon, double locationLat, String field) {
        return new SearchSortGeo(locationLon, locationLat, field);
    }

}
