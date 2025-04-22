
package com.couchbase.client.core;

public class ServiceNotAvailableException extends CouchbaseException {

    public ServiceNotAvailableException() {
    }

    public ServiceNotAvailableException(String message) {
        super(message);
    }

    public ServiceNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceNotAvailableException(Throwable cause) {
        super(cause);
    }
}
