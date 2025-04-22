package com.couchbase.client.java.error;

import com.couchbase.client.core.CouchbaseException;

public class FtsMalformedRequestException extends CouchbaseException {

    public FtsMalformedRequestException(String payload) {
        super("FTS request is malformed. Details: " + payload);
    }
}
