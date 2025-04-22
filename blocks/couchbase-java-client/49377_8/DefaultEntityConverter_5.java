package com.couchbase.client.java.repository;

import java.util.concurrent.TimeUnit;

public interface Repository {

    AsyncRepository async();

    <T> T get(String id, Class<T> entityClass);
    <T> T get(String id, Class<T> entityClass, long timeout, TimeUnit timeUnit);

    <T> T upsert(T document);
    <T> T upsert(T document, long timeout, TimeUnit timeUnit);

}
