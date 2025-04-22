package com.couchbase.client.java.error;

import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.java.util.Retry;

public class CannotRetryException extends CouchbaseException {

    public CannotRetryException(String message) {
        super(message);
    }

    public CannotRetryException(String message, Throwable cause) {
        super(message, cause);
    }
}
