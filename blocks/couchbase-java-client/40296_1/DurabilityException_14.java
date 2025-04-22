package com.couchbase.client.java.error;

import com.couchbase.client.core.CouchbaseException;

public class DocumentDoesNotExistException extends CouchbaseException {

    public DocumentDoesNotExistException() {
    }

    public DocumentDoesNotExistException(String message) {
        super(message);
    }

    public DocumentDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public DocumentDoesNotExistException(Throwable cause) {
        super(cause);
    }
}
