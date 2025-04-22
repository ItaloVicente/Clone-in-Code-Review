package com.couchbase.client.java.error.subdoc;

import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class ValueTooDeepException extends CouchbaseException {

    public ValueTooDeepException(String id, String path) {
        super("Provided value makes the path " + path + " too deep in " + id);
    }
}
