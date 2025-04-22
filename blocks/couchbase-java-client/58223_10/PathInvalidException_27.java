package com.couchbase.client.java.error.subdoc;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.PersistTo;
import com.couchbase.client.java.ReplicateTo;
import com.couchbase.client.java.document.subdoc.DocumentFragment;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class PathExistsException extends SubDocumentException {

    public PathExistsException(String id, String path) {
        super("Path " + path + " already exist in document " + id);
    }

    public PathExistsException(String reason) {
        super(reason);
    }
}
