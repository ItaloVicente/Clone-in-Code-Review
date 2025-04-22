package com.couchbase.client.core;

public class ReplicaNotAvailableException extends CouchbaseException {

    public ReplicaNotAvailableException() {
    }

    public ReplicaNotAvailableException(String message) {
        super(message);
    }

    public ReplicaNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReplicaNotAvailableException(Throwable cause) {
        super(cause);
    }
}
