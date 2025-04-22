
package com.couchbase.client.java.error;

import java.util.List;

import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.bucket.BucketManager;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class IndexAlreadyExistsException extends CouchbaseException {

    public IndexAlreadyExistsException() {
    }

    public IndexAlreadyExistsException(String message) {
        super(message);
    }

    public IndexAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public IndexAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
