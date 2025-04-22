package com.couchbase.client.core;

public class DocumentMutationLostException extends CouchbaseException {

    public DocumentMutationLostException() {
    }

    public DocumentMutationLostException(String message) {
        super(message);
    }

    public DocumentMutationLostException(String message, Throwable cause) {
        super(message, cause);
    }

    public DocumentMutationLostException(Throwable cause) {
        super(cause);
    }
}
