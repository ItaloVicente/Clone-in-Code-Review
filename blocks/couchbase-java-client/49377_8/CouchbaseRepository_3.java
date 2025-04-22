package com.couchbase.client.java.repository;

import com.couchbase.client.java.AsyncBucket;
import com.couchbase.client.java.document.Document;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.repository.mapping.DefaultEntityConverter;
import com.couchbase.client.java.repository.mapping.EntityConverter;
import rx.Observable;
import rx.functions.Func1;

public class CouchbaseAsyncRepository implements AsyncRepository {

    private final EntityConverter converter;
    private final AsyncBucket bucket;

    public CouchbaseAsyncRepository(AsyncBucket bucket) {
        this.bucket = bucket;
        converter = new DefaultEntityConverter();
    }

    @Override
    public <T> Observable<T> get(String id, final Class<T> documentClass) {
        return Observable
            .just(id)
            .flatMap(new Func1<String, Observable<JsonDocument>>() {
                @Override
                public Observable<JsonDocument> call(String id) {
                    return bucket.get(id);
                }
            })
            .map(new Func1<JsonDocument, T>() {
                @Override
                public T call(JsonDocument document) {
                    return (T) converter.toEntity(document, documentClass);
                }
            });
    }

    @Override
    public <T> Observable<T> upsert(final T document) {
        return Observable
            .just(document)
            .flatMap(new Func1<T, Observable<? extends Document<?>>>() {
                @Override
                public Observable<? extends Document<?>> call(T source) {
                    Document<?> converted = converter.fromEntity(source);
                    return bucket.upsert(converted);
                }
            }).map(new Func1<Document<?>, T>() {
                @Override
                public T call(Document<?> stored) {
                    return document;
                }
            });
    }
}
