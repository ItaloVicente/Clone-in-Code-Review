
package com.couchbase.client.java.error.subdoc;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class DocumentNotJsonException extends SubDocumentException {

    public DocumentNotJsonException(String id) {
        super("Document " + id + " is not a JSON document");
    }
}
