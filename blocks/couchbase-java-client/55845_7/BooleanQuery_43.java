
package com.couchbase.client.java.search.fulltext;

import com.couchbase.client.java.document.json.JsonObject;

public class FullTextStore {
    public static final String KV_STORE_NAME = "forestdb";

    private final String kvStoreName;

    protected FullTextStore(Builder builder) {
        kvStoreName = builder.kvStoreName;
    }

    public String kvStoreName() {
        return kvStoreName;
    }

    public static Builder builder() {
        return new Builder();
    }

    public JsonObject json() {
        JsonObject json = JsonObject.create();
        json.put("kvStoreName", kvStoreName);
        return json;
    }

    public static class Builder {

        private String kvStoreName = KV_STORE_NAME;

        protected Builder() {
        }

        public FullTextStore build() {
            return new FullTextStore(this);
        }

        public Builder kvStoreName(String kvStoreName) {
            this.kvStoreName = kvStoreName;
            return this;
        }
    }

}
