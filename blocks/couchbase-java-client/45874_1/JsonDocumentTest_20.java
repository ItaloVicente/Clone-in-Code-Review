package com.couchbase.client.java.error;

import com.couchbase.client.core.CouchbaseException;

public class TemporaryFailureException extends CouchbaseException {

    public TemporaryFailureException() {
    }

    public TemporaryFailureException(String message) {
        super(message);
    }

    public TemporaryFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public TemporaryFailureException(Throwable cause) {
        super(cause);
    }
}
