package com.couchbase.client.java.search.sort;

import com.couchbase.client.java.document.json.JsonObject;

public class SearchSortField extends SearchSort {

    private final String field;

    private FieldType type;
    private FieldMode mode;
    private FieldMissing missing;

    public SearchSortField(String field) {
        this.field = field;
    }

    @Override
    public SearchSortField descending(boolean descending) {
        super.descending(descending);
        return this;
    }

    public SearchSortField type(FieldType type) {
        this.type = type;
        return this;
    }

    public SearchSortField mode(FieldMode mode) {
        this.mode = mode;
        return this;
    }

    public SearchSortField missing(FieldMissing missing) {
        this.missing = missing;
        return this;
    }

    @Override
    protected String identifier() {
        return "field";
    }

    @Override
    public void injectParams(JsonObject queryJson) {
        super.injectParams(queryJson);

        queryJson.put("field", field);

        if (type != null) {
            queryJson.put("type", type.value());
        }
        if (mode != null) {
            queryJson.put("mode", mode.value());
        }
        if (missing != null) {
            queryJson.put("missing", missing.value());
        }
    }
}
