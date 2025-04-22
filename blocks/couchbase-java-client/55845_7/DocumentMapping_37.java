
package com.couchbase.client.java.search.alias;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.search.IndexParams;
import com.couchbase.client.java.search.IndexSettings;

public class AliasIndexSettings implements IndexSettings {
    public static final String TYPE = "alias";
    private final String name;
    private final AliasIndexParams params;

    public AliasIndexSettings(Builder builder) {
        name = builder.name;
        params = builder.params;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String type() {
        return TYPE;
    }

    @Override
    public IndexParams params() {
        return params();
    }

    public JsonObject json() {
        JsonObject json = JsonObject.create();
        json.put("name", name);
        json.put("type", TYPE);
        json.put("params", params.json().toString());
        json.put("sourceType", "nil");
        json.put("sourceName", "");
        json.put("sourceUUID", "");
        json.put("sourceParams", "null");
        return json;
    }

    public static class Builder {

        public AliasIndexParams params;
        public String name;

        protected Builder() {
        }

        public AliasIndexSettings build() {
            return new AliasIndexSettings(this);
        }

        public Builder params(AliasIndexParams params) {
            this.params = params;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

    }
}
