package com.couchbase.client.java.error;

import com.couchbase.client.core.CouchbaseException;

public class DurabilityException extends CouchbaseException {

    public DurabilityException() {
    }

    public DurabilityException(String message) {
        super(message);
    }

    public DurabilityException(String message, Throwable cause) {
        super(message, cause);
    }

    public DurabilityException(Throwable cause) {
        super(cause);
    }
}
