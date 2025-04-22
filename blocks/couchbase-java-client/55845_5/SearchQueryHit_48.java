
package com.couchbase.client.java.search.query;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;

public abstract class SearchQuery {
    public static final int SIZE = 10;
    public static final int FROM = 0;
    private static final boolean EXPLAIN = false;
    private static final String HIGHLIGHT_STYLE = "html"; /* html, ansi */

    private final int size;
    private final int from;
    private final String index;
    private final boolean explain;
    private final String highlightStyle;
    private final String[] highlightFields;
    private final String[] fields;
    private final SearchControl control;

    protected SearchQuery(Builder builder) {
        size = builder.size;
        from = builder.from;
        index = builder.index;
        explain = builder.explain;
        highlightStyle = builder.highlightStyle;
        highlightFields = builder.highlightFields;
        fields = builder.fields;
        control = builder.control;
    }

    public int size() {
        return size;
    }

    public int from() {
        return from;
    }

    public String index() {
        return index;
    }

    public String[] fields() {
        return fields;
    }

    public SearchControl control() {
        return control;
    }

    public abstract JsonObject queryJson();

    public JsonObject json() {
        JsonObject json = JsonObject.create();
        json.put("query", queryJson());
        JsonObject highlightJson = JsonObject.create();
        if (highlightStyle != null) {
            highlightJson.put("style", highlightStyle);
        }
        if (highlightFields != null) {
            highlightJson.put("fields", JsonArray.from(highlightFields));
        }
        if (highlightJson.size() > 0) {
            json.put("highlight", highlightJson);
        }
        if (fields != null) {
            json.put("fields", JsonArray.from(fields));
        }
        json.put("size", size);
        json.put("from", from);
        json.put("explain", explain);
        if (control != null) {
            json.put("ctl", control.json());
        }
        return json;
    }

    public static abstract class Builder {
        public boolean explain = EXPLAIN;
        public String highlightStyle = HIGHLIGHT_STYLE;
        public String[] highlightFields;
        public String[] fields;
        public SearchControl control = null;
        private int size = SIZE;
        private int from = FROM;
        private String index;

        protected Builder() {
        }

        public abstract SearchQuery build();

        public Builder index(String index) {
            this.index = index;
            return this;
        }

        public Builder size(int size) {
            this.size = size;
            return this;
        }

        public Builder from(int from) {
            this.from = from;
            return this;
        }

        public Builder explain(boolean explain) {
            this.explain = explain;
            return this;
        }

        public Builder highlightStyle(String highlightStyle) {
            this.highlightStyle = highlightStyle;
            return this;
        }

        public Builder highlightFields(String... highlightFields) {
            this.highlightFields = highlightFields;
            return this;
        }

        public Builder fields(String... fields) {
            this.fields = fields;
            return this;
        }

        public Builder control(SearchControl control) {
            this.control = control;
            return this;
        }
    }
}
