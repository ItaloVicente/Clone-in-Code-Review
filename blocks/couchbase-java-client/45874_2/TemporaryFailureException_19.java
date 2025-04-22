package com.couchbase.client.java.error;

import com.couchbase.client.core.CouchbaseException;

public class RequestTooBigException extends CouchbaseException {

    public RequestTooBigException() {
    }

    public RequestTooBigException(String message) {
        super(message);
    }

    public RequestTooBigException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestTooBigException(Throwable cause) {
        super(cause);
    }
}
