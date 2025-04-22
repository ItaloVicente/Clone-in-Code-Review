
package com.couchbase.client.java.error.subdoc;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.error.TranscodingException;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class CannotInsertValueException extends SubDocumentException {

    public CannotInsertValueException(String reason) {
        super(reason);
    }
}
