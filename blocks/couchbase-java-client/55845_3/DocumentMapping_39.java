
package com.couchbase.client.java.search.bleve;

import com.couchbase.client.java.document.json.JsonObject;

public class BleveStore {
    public static final String KV_STORE_NAME = "forestdb";

    private final String kvStoreName;

    protected BleveStore(Builder builder) {
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

        public BleveStore build() {
            return new BleveStore(this);
        }

        public Builder kvStoreName(String kvStoreName) {
            this.kvStoreName = kvStoreName;
            return this;
        }
    }

}
