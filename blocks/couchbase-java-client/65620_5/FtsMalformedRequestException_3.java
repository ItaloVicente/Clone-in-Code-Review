package com.couchbase.client.java.error;

import com.couchbase.client.core.CouchbaseException;

public class FtsConsistencyTimeoutException extends CouchbaseException {

    public FtsConsistencyTimeoutException() {
        super("The requested consistency level could not be satisfied before the timeout was reached");
    }
}
