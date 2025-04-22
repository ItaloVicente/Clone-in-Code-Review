package com.couchbase.client.java.repository;

import com.couchbase.client.java.AsyncBucket;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.repository.mapping.EntityProperties;
import com.couchbase.client.java.repository.mapping.ReflectionBasedEntityProperties;
import com.couchbase.client.java.repository.mapping.RepositoryMappingException;
import rx.Observable;
import rx.functions.Func1;

import java.lang.reflect.Field;

public class CouchbaseAsyncRepository implements AsyncRepository {

    private final AsyncBucket bucket;

    public CouchbaseAsyncRepository(AsyncBucket bucket) {
        this.bucket = bucket;
    }

    @Override
    public <T> Observable<T> get(String id, Class<T> documentClass) {
        return null;
    }

    @Override
    public <T> Observable<T> upsert(final T document) {
        EntityProperties properties = new ReflectionBasedEntityProperties(document.getClass());

        if (!properties.hasIdProperty()) {
            return Observable.error(new RepositoryMappingException("No Id Field annotated with @Id present."));
        }

        String id = properties.get(properties.idProperty(), document, String.class);
        if (id == null) {
            return Observable.error(new RepositoryMappingException("Id Field cannot be null."));
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
                return Observable.error(new RepositoryMappingException("Unsupported field type: " + type));
            }
        }

        return bucket
            .upsert(JsonDocument.create(id, content))
            .map(new Func1<JsonDocument, T>() {
                @Override
                public T call(JsonDocument stored) {
                    return document;
                }
            });
    }
}
