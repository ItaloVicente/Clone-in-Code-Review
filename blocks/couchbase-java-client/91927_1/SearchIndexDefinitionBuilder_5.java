
package com.couchbase.client.java.cluster;

import java.util.UUID;

import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonObject;

@InterfaceStability.Experimental
public class SearchIndexDefinitionBuilder {

    private final String indexName;
    private final String sourceName;
    private final SearchIndexSourceType sourceType;
    private UUID uuid;
    private JsonObject indexParams;
    private JsonObject planParams;
    private JsonObject sourceParams;
    private UUID prevIndexUUID;
    private UUID sourceUUID;
    private SearchIndexType type = SearchIndexType.INDEX;

    public SearchIndexDefinitionBuilder(String indexName) {
        this(indexName, null, null);
        this.type = SearchIndexType.ALIAS;
    }


    public SearchIndexDefinitionBuilder(String indexName,
                                        String sourceName,
                                        SearchIndexSourceType sourceType) {
        this.indexName = indexName;
        this.sourceName = sourceName;
        this.sourceType = sourceType;
    }

    public SearchIndexDefinitionBuilder setUUID(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public SearchIndexDefinitionBuilder setPlanParams(JsonObject planParams) {
        this.planParams = planParams;
        return this;
    }

    public SearchIndexDefinitionBuilder setIndexParams(JsonObject indexParams) {
        this.indexParams = indexParams;
        return this;
    }

    public SearchIndexDefinitionBuilder setSourceParams(JsonObject sourceParams) {
        this.sourceParams = sourceParams;
        return this;
    }

    public SearchIndexDefinitionBuilder setPrevIndexUUID(UUID uuid) {
        this.prevIndexUUID = uuid;
        return this;
    }

    public SearchIndexDefinitionBuilder setSourceUUID(UUID uuid) {
        this.sourceUUID = uuid;
        return this;
    }

    public SearchIndexType getType() {
        return type;
    }

    public SearchIndexSourceType getSourceType() {
        return sourceType;
    }

    public UUID getUUID() {
        return uuid;
    }

    public JsonObject getIndexParams() {
        return indexParams;
    }

    public JsonObject getPlanParams() {
        return planParams;
    }

    public JsonObject getSourceParams() {
        return sourceParams;
    }

    public String getIndexName() {
        return indexName;
    }

    public String getSourceName() {
        return sourceName;
    }

    public UUID getPrevIndexUUID() {
        return prevIndexUUID;
    }

    public UUID getSourceUUID() {
        return sourceUUID;
    }

    @Override
    public String toString() {
        JsonObject indexDefinition = JsonObject.create();
        indexDefinition.put("name", indexName);
        indexDefinition.put("type", type.toString());

        if (uuid != null) {
            indexDefinition.put("uuid", uuid.toString());
        }

        if (sourceType == null) {
            indexDefinition.put("sourceType", "nil");
        } else {
            indexDefinition.put("sourceType", sourceType.toString());
        }

        if (indexParams != null) {
            indexDefinition.put("params", indexParams.toString());
        }

        if (planParams != null) {
            indexDefinition.put("planParams", planParams.toString());
        }

        if (prevIndexUUID != null) {
            indexDefinition.put("prevIndexUUID", prevIndexUUID.toString());
        }

        if (sourceName != null) {
            indexDefinition.put("sourceName", sourceName);
        }

        if (sourceParams != null) {
            indexDefinition.put("sourceParams", sourceParams.toString());
        }

        if (sourceUUID != null) {
            indexDefinition.put("sourceUUID", sourceUUID.toString());
        }

        return indexDefinition.toString();
    }
}
