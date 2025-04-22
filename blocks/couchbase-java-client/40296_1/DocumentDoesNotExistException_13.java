package com.couchbase.client.java.error;

import com.couchbase.client.core.CouchbaseException;

public class DocumentAlreadyExistsException extends CouchbaseException {

    public DocumentAlreadyExistsException() {
    }

    public DocumentAlreadyExistsException(String message) {
        super(message);
    }

    public DocumentAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public DocumentAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
