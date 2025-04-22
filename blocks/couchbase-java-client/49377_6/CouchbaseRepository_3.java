package com.couchbase.client.java.repository;

import com.couchbase.client.java.AsyncBucket;
import com.couchbase.client.java.document.Document;
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
    public <T> Observable<T> get(String id, Class<T> documentClass) {
        return null;
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
