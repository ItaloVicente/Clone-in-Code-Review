package com.couchbase.client.java.transcoder;

import com.couchbase.client.core.CouchbaseException;

public class TranscodingException extends CouchbaseException {

    public TranscodingException() {
    }

    public TranscodingException(String message) {
        super(message);
    }

    public TranscodingException(String message, Throwable cause) {
        super(message, cause);
    }

    public TranscodingException(Throwable cause) {
        super(cause);
    }
}
