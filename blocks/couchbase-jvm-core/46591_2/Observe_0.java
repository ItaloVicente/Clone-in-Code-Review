package com.couchbase.client.core;

public class DocumentConcurrentlyModifiedException extends CouchbaseException {

    public DocumentConcurrentlyModifiedException() {
    }

    public DocumentConcurrentlyModifiedException(String message) {
        super(message);
    }

    public DocumentConcurrentlyModifiedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DocumentConcurrentlyModifiedException(Throwable cause) {
        super(cause);
    }
}
