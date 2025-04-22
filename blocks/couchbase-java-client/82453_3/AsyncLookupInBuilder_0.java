package com.couchbase.client.java.error.subdoc;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Committed
@InterfaceAudience.Public
public class XattrOrderingException extends SubDocumentException {

    public XattrOrderingException(String message) {
        super(message);
    }

    public XattrOrderingException(String message, Throwable cause) {
        super(message, cause);
    }

    public XattrOrderingException(Throwable cause) {
        super(cause);
    }

}
