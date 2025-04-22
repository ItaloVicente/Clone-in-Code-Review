package com.couchbase.client.java.error;

import java.util.concurrent.TimeUnit;

import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.java.bucket.BucketManager;

public class IndexesNotReadyException extends CouchbaseException {
    public IndexesNotReadyException() {
    }

    public IndexesNotReadyException(String message) {
        super(message);
    }
}
