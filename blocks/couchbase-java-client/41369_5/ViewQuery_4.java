package com.couchbase.client.java.error;

import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.java.view.ViewResult;

public class ViewQueryException extends CouchbaseException {
    private String error;
    private String reason;

    public ViewQueryException() {
        super();
    }

    public ViewQueryException(String message) {
        super(message);
    }

    public ViewQueryException(String message, Throwable cause) {
        super(message, cause);
    }

    public ViewQueryException(Throwable cause) {
        super(cause);
    }

    public ViewQueryException(String error, String reason, Throwable cause) {
        super(error + ": " + reason, cause);
        this.error = error;
        this.reason = reason;
    }

    public ViewQueryException(String error, String reason) {
        super(error + ": " + reason);
        this.error = error;
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public String getError() {
        return error;
    }
}
