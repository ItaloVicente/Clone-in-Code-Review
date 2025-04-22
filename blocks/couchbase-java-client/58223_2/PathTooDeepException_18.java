package com.couchbase.client.java.error.subdoc;

import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class PathNotFoundException extends CouchbaseException {

    public PathNotFoundException(String id, String path) {
        super("Path " + path + " not found in document " + id);
    }
}
