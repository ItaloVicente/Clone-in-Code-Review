package com.couchbase.client.java.repository.mapping;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;

import java.lang.reflect.Field;

public class DefaultEntityConverter implements EntityConverter<JsonDocument> {

    @Override
    public JsonDocument fromEntity(Object document) {
        EntityProperties properties = new ReflectionBasedEntityProperties(document.getClass());

        if (!properties.hasIdProperty()) {
            throw new RepositoryMappingException("No Id Field annotated with @Id present.");
        }

        String id = properties.get(properties.idProperty(), document, String.class);
        if (id == null) {
            throw new RepositoryMappingException("Id Field cannot be null.");
        }

        JsonObject content = JsonObject.create();

        for (Field field : properties.fieldProperties()) {
            String name = field.getName();
            Class<?> type = field.getType();
            Object value = properties.get(field, document, Object.class);

            if (value == null
                || value instanceof String
                || value instanceof Boolean
                || value instanceof Integer
                || value instanceof Long
                || value instanceof Double) {
                content.put(name, value);
            } else {
                throw new RepositoryMappingException("Unsupported field type: " + type);
            }
        }
        return JsonDocument.create(id, content);
    }

}
