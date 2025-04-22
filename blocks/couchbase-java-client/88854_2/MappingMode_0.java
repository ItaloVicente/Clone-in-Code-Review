package com.couchbase.client.java.repository.mapping;

import com.couchbase.client.java.document.EntityDocument;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonEntityConverter implements EntityConverter<JsonDocument> {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    @Override
    public JsonDocument fromEntity(EntityDocument<Object> source) {
        Object documentContent = source.content();

        if (documentContent == null) {
            throw new RepositoryMappingException("The content of the document may not be null");
        }

        String serializedEntity = GSON.toJson(documentContent);
        JsonObject jsonObject = JsonObject.fromJson(serializedEntity);

        String id = jsonObject.getString("id");
        if (id == null) {
            throw new RepositoryMappingException("The id field cannot be null or empty.");
        }

        return JsonDocument.create(id, source.expiry(), jsonObject, source.cas());
    }

    @Override
    public <T> EntityDocument<T> toEntity(JsonDocument source, Class<T> entityClass) {
        T documentContent = GSON.fromJson(source.content().toString(), entityClass);

        if (documentContent == null) {
            throw new RepositoryMappingException("Could not create entity document from json document.");
        }

        return EntityDocument.create(source.id(), source.expiry(), documentContent, source.cas());
    }
}
