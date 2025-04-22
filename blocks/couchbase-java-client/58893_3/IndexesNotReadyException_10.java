
package com.couchbase.client.java.error;

import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.bucket.BucketManager;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class IndexDoesNotExistException extends CouchbaseException {

    public IndexDoesNotExistException() {
    }

    public IndexDoesNotExistException(String message) {
        super(message);
    }

    public IndexDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public IndexDoesNotExistException(Throwable cause) {
        super(cause);
    }
}
