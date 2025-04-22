
package com.couchbase.client.java.search.fulltext;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.search.IndexParams;

public class FullTextIndexParams implements IndexParams {
    private final FullTextIndexMapping mapping;
    private final FullTextStore store;

    public FullTextIndexParams(Builder builder) {
        mapping = builder.mapping;
        store = builder.store;
    }

    public FullTextIndexMapping mapping() {
        return mapping;
    }

    public FullTextStore store() {
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

        private FullTextIndexMapping mapping = FullTextIndexMapping.builder().build();
        public FullTextStore store = FullTextStore.builder().build();

        protected Builder() {
        }

        public FullTextIndexParams build() {
            return new FullTextIndexParams(this);
        }

        public Builder mapping(FullTextIndexMapping mapping) {
            this.mapping = mapping;
            return this;
        }

        public Builder store(FullTextStore store) {
            this.store = store;
            return this;
        }
    }

}
