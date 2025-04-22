package com.couchbase.client.java.error;

import com.couchbase.client.core.CouchbaseException;

public class FtsServerOverloadException extends CouchbaseException {

    public FtsServerOverloadException(String payload) {
        super("Search server is overloaded. Details: " + payload);
    }
}
