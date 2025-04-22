
package com.couchbase.client.java.search.fulltext;

import com.couchbase.client.java.document.json.JsonObject;

public class FieldMapping {
    public static final String TYPE = "text";
    public static final boolean STORE = true;
    public static final boolean INDEX = true;
    public static final boolean INCLUDE_TERM_VECTORS = true;
    public static final boolean INCLUDE_IN_ALL = true;

    private final String name;
    private final String type;
    private final String analyzer;
    private final boolean store;
    private final boolean index;
    private final boolean includeTermVectors;
    private final boolean includeInAll;
    private final String dateFormat;

    public FieldMapping(Builder builder) {
        name = builder.name;
        type = builder.type;
        analyzer = builder.analyzer;
        store = builder.store;
        index = builder.index;
        includeTermVectors = builder.includeTermVectors;
        includeInAll = builder.includeInAll;
        dateFormat = builder.dateFormat;
    }

    public String name() {
        return name;
    }

    public String type() {
        return type;
    }

    public String analyzer() {
        return analyzer;
    }

    public boolean store() {
        return store;
    }

    public boolean index() {
        return index;
    }

    public boolean includeTermVectors() {
        return includeTermVectors;
    }

    public boolean includeInAll() {
        return includeInAll;
    }

    public String dateFormat() {
        return dateFormat;
    }

    public static Builder builder() {
        return new Builder();
    }

    public JsonObject json() {
        JsonObject json = JsonObject.create();
        json.put("name", name);
        json.put("type", type);
        if (analyzer != null) {
            json.put("analyzer", analyzer);
        }
        json.put("store", store);
        json.put("index", index);
        json.put("includeTermVectors", includeTermVectors);
        json.put("includeInAll", includeInAll);
        if (dateFormat != null) {
            json.put("dateFormat", dateFormat);
        }
        return json;
    }

    public static class Builder {
        public String name;
        public String type = TYPE;
        public String analyzer;
        public boolean store = STORE;
        public boolean index = INDEX;
        public boolean includeTermVectors = INCLUDE_TERM_VECTORS;
        public boolean includeInAll = INCLUDE_IN_ALL;
        public String dateFormat;

        protected Builder() {
        }

        public FieldMapping build() {
            return new FieldMapping(this);
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder analyzer(String analyzer) {
            this.analyzer = analyzer;
            return this;
        }

        public Builder store(boolean store) {
            this.store = store;
            return this;
        }

        public Builder index(boolean index) {
            this.index = index;
            return this;
        }

        public Builder includeTermVectors(boolean includeTermVectors) {
            this.includeTermVectors = includeTermVectors;
            return this;
        }

        public Builder includeInAll(boolean includeInAll) {
            this.includeInAll = includeInAll;
            return this;
        }

        public Builder dateFormat(String dateFormat) {
            this.dateFormat = dateFormat;
            return this;
        }
    }
}
