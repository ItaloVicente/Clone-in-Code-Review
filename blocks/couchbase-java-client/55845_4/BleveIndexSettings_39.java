
package com.couchbase.client.java.search.bleve;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.search.IndexParams;

public class BleveIndexParams implements IndexParams {
    private final BleveIndexMapping mapping;
    private final BleveStore store;

    public BleveIndexParams(Builder builder) {
        mapping = builder.mapping;
        store = builder.store;
    }

    public BleveIndexMapping mapping() {
        return mapping;
    }

    public BleveStore store() {
        return store;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public JsonObject json() {
        JsonObject json = JsonObject.create();
        json.put("mapping", mapping.json());
        json.put("store", store.json());
        return json;
    }

    public static class Builder {

        private BleveIndexMapping mapping = BleveIndexMapping.builder().build();
        public BleveStore store = BleveStore.builder().build();

        protected Builder() {
        }

        public BleveIndexParams build() {
            return new BleveIndexParams(this);
        }

        public Builder mapping(BleveIndexMapping mapping) {
            this.mapping = mapping;
            return this;
        }

        public Builder store(BleveStore store) {
            this.store = store;
            return this;
        }
    }

}
