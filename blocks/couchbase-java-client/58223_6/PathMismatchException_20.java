package com.couchbase.client.java.error.subdoc;

import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class PathInvalidException extends CouchbaseException {

    public PathInvalidException(String id, String path) {
        super("Path " + path + " is malformed or invalid in " + id);
    }

    public PathInvalidException(String reason) {
        super(reason);
    }
}
