package com.couchbase.client.java.repository;

import rx.Observable;

public interface AsyncRepository {

    <T> Observable<T> get(String id, Class<T> documentClass);

    <T> Observable<T> upsert(T document);

}
