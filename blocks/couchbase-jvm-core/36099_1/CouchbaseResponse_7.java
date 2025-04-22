package com.couchbase.client.core.message;

import rx.subjects.Subject;

public class AbstractCouchbaseResponse implements CouchbaseResponse {

    private Subject<CouchbaseResponse, CouchbaseResponse> observable;

    @Override
    public CouchbaseResponse observable(final Subject<CouchbaseResponse, CouchbaseResponse> observable) {
        this.observable = observable;
        return this;
    }

    @Override
    public Subject<CouchbaseResponse, CouchbaseResponse> observable() {
        return observable;
    }
}
