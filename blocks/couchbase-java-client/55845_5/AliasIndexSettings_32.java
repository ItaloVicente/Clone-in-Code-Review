
package com.couchbase.client.java.search.alias;

import com.couchbase.client.java.document.json.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class AliasIndexParams {
    public Map<String, String> targets;

    public AliasIndexParams(Builder builder) {
        targets = builder.targets;
    }

    public static Builder builder() {
        return new Builder();
    }

    public JsonObject json() {
        JsonObject json = JsonObject.create();
        JsonObject targetsJson = JsonObject.create();
        if (targets != null && !targets.isEmpty()) {
            for (Map.Entry<String, String> entry : targets.entrySet()) {
                JsonObject uuid = JsonObject.create();
                uuid.put("indexUUID", entry.getValue());
                targetsJson.put(entry.getKey(), uuid);
            }
        }
        json.put("targets", targetsJson);
        return json;
    }

    public static class Builder {

        public Map<String, String> targets = new HashMap<String, String>();

        protected Builder() {
        }

        public AliasIndexParams build() {
            return new AliasIndexParams(this);
        }

        public Builder targets(Map<String, String> targets) {
            this.targets = targets;
            return this;
        }
        public Builder target(String indexName, String uuid) {
            targets.put(indexName, uuid);
            return this;
        }

        public Builder target(String indexName) {
            targets.put(indexName, "");
            return this;
        }

    }

}
