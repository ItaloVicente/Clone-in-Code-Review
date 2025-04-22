package com.couchbase.client.java.error.subdoc;

import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.java.error.TranscodingException;

public class CannotInsertValueException extends CouchbaseException {

    public CannotInsertValueException(String reason) {
        super(reason);
    }
}
