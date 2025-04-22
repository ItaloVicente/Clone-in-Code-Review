package com.couchbase.client.java.error;

import com.couchbase.client.core.CouchbaseException;

public class BucketAlreadyExistsException extends CouchbaseException {

    public BucketAlreadyExistsException() {
    }

    public BucketAlreadyExistsException(String message) {
        super(message);
    }

    public BucketAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public BucketAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
