package com.couchbase.client.java.repository;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.util.Blocking;

import java.util.concurrent.TimeUnit;

public class CouchbaseRepository implements Repository {

    private static final TimeUnit TIMEOUT_UNIT = TimeUnit.MILLISECONDS;

    private final AsyncRepository asyncRepository;
    private final Bucket bucket;
    private final long timeout;

    public CouchbaseRepository(Bucket bucket, CouchbaseEnvironment environment) {
        this.bucket = bucket;
        this.timeout = environment.kvTimeout();
        this.asyncRepository = bucket.async().repository().toBlocking().single();
    }

    @Override
    public AsyncRepository async() {
        return asyncRepository;
    }

    @Override
    public <T> T get(String id, Class<T> entityClass) {
        return get(id, entityClass, timeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> T get(String id, Class<T> entityClass, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncRepository.get(id, entityClass), timeout, timeUnit);
    }

    @Override
    public <T> T upsert(T document) {
        return upsert(document, timeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> T upsert(T document, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncRepository.upsert(document), timeout, timeUnit);
    }

}
