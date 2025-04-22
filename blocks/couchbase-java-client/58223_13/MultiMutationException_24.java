
package com.couchbase.client.java.error.subdoc;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class DocumentTooDeepException extends SubDocumentException {

    public DocumentTooDeepException(String id) {
        super("JSON is too deep in document " + id);
    }
}
