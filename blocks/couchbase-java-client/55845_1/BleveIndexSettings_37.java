
package com.couchbase.client.java.search.bleve;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.search.IndexParams;
import com.couchbase.client.java.search.IndexSettings;
import com.couchbase.client.java.search.PlanParams;
import com.couchbase.client.java.search.SourceParams;

public class BleveIndexSettings implements IndexSettings {
    public static final String TYPE = "bleve";
    private final String name;
    private final BleveIndexParams params;
    private final String sourceType;
    private final String sourceName;
    private final String sourceUUID;
    private final SourceParams sourceParams;
    private final PlanParams planParams;

    public BleveIndexSettings(Builder builder) {
        name = builder.name;
        params = builder.params;
        sourceType = builder.sourceType;
        sourceName = builder.sourceName;
        sourceUUID = builder.sourceUUID;
        sourceParams = builder.sourceParams;
        planParams = builder.planParams;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String name() {
        return name;
    }

    public String type() {
        return TYPE;
    }

    public IndexParams params() {
        return params;
    }

    public String sourceType() {
        return sourceType;
    }

    public String sourceName() {
        return sourceName;
    }

    public String sourceUUID() {
        return sourceUUID;
    }

    public SourceParams sourceParams() {
        return sourceParams;
    }

    public PlanParams planParams() {
        return planParams;
    }

    @Override
    public JsonObject json() {
        JsonObject json = JsonObject.create();
        json.put("name", name);
        json.put("type", TYPE);
        json.put("params", params.json().toString());
        json.put("sourceType", sourceType);
        json.put("sourceName", sourceName);
        json.put("sourceUUID", sourceUUID);
        json.put("sourceParams", sourceParams.json().toString());
        json.put("planParams", planParams.json());
        return json;
    }

    public static class Builder {

        public BleveIndexParams params = BleveIndexParams.builder().build();
        public PlanParams planParams = PlanParams.builder().build();
        public String sourceType;
        public String sourceName;
        public SourceParams sourceParams;
        public String sourceUUID;
        public String name;


        protected Builder() {
        }

        public BleveIndexSettings build() {
            return new BleveIndexSettings(this);
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder params(BleveIndexParams params) {
            this.params = params;
            return this;
        }

        public Builder planParams(PlanParams planParams) {
            this.planParams = planParams;
            return this;
        }

        public Builder sourceType(String sourceType) {
            this.sourceType = sourceType;
            return this;
        }

        public Builder sourceName(String sourceName) {
            this.sourceName = sourceName;
            return this;
        }

        public Builder sourceUUID(String sourceUUID) {
            this.sourceUUID = sourceUUID;
            return this;
        }

        public Builder sourceParams(SourceParams sourceParams) {
            this.sourceParams = sourceParams;
            return this;
        }
    }
}
