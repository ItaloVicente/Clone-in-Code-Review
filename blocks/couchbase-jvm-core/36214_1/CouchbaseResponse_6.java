package com.couchbase.client.core.message;

public abstract class AbstractCouchbaseResponse implements CouchbaseResponse {

    private final ResponseStatus status;

    protected AbstractCouchbaseResponse(final ResponseStatus status) {
        this.status = status;
    }

    @Override
    public ResponseStatus status() {
        return status;
    }
}
