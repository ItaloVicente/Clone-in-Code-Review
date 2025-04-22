package com.couchbase.client.java.fts.facet;


import com.couchbase.client.java.document.json.JsonObject;

public abstract class SearchFacet {

    private final String name;
    private final String field;
    private final int limit;

    protected SearchFacet(String name, String field, int limit) {
        this.name = name;
        this.field = field;
        this.limit = limit;
    }

    public static TermFacet term(String name, String field, int limit) {
        return new TermFacet(name, field, limit);
    }

    public static NumericRangeFacet numeric(String name, String field, int limit) {
        return new NumericRangeFacet(name, field, limit);
    }

    public static DateRangeFacet date(String name, String field, int limit) {
        return new DateRangeFacet(name, field, limit);
    }

    public String name() {
        return name;
    }

    public void injectParams(JsonObject queryJson) {
        queryJson.put("size", limit);
        queryJson.put("field", field);
    }

}
