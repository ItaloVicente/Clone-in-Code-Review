package com.couchbase.client.java.error;

import com.couchbase.client.core.CouchbaseException;

public class CouchbaseOutOfMemoryException extends CouchbaseException {

    public CouchbaseOutOfMemoryException() {
    }

    public CouchbaseOutOfMemoryException(String message) {
        super(message);
    }

    public CouchbaseOutOfMemoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouchbaseOutOfMemoryException(Throwable cause) {
        super(cause);
    }
}
