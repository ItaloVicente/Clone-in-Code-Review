
package com.couchbase.client.core;

import java.util.concurrent.TimeoutException;

public class TraceableTimeoutException extends TimeoutException {

    private final String traceIdentifier;

    public TraceableTimeoutException(final String traceIdentifier) {
        this(traceIdentifier, null);
    }

    public TraceableTimeoutException(final String traceIdentifier, final String message) {
        super(message);
        this.traceIdentifier = traceIdentifier;
    }

    public String traceIdentifier() {
        return traceIdentifier;
    }

    @Override
    public String toString() {
        return "TraceableTimeoutException{" +
            "traceIdentifier='" + traceIdentifier + '\'' +
            ", message='" + getMessage() + '\'' +
            '}';
    }
}
