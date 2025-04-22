
package com.couchbase.client.java.error.subdoc;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class PathTooDeepException extends SubDocumentException {

    public PathTooDeepException(String path) {
        super("Path too deep: " + path);
    }
}
