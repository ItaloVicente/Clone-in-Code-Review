package com.couchbase.client.java.error.subdoc;

import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public abstract class SubDocumentException extends CouchbaseException {

    protected SubDocumentException() {
    }

    protected SubDocumentException(String message) {
        super(message);
    }

    protected SubDocumentException(String message, Throwable cause) {
        super(message, cause);
    }

    protected SubDocumentException(Throwable cause) {
        super(cause);
    }
}
