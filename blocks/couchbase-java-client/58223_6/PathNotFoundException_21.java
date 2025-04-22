package com.couchbase.client.java.error.subdoc;

import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class PathMismatchException extends CouchbaseException {

    public PathMismatchException(String id, String path) {
        super("Path mismatch \"" + path + "\" in " + id);
    }

    public PathMismatchException(String reason) {
        super(reason);
    }
}
