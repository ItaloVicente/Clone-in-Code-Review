package com.couchbase.client.java.error;

import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.java.auth.PasswordAuthenticator;

public class MixedAuthenticationException extends CouchbaseException {

    public MixedAuthenticationException() {
        super();
    }

    public MixedAuthenticationException(String message) {
        super(message);
    }

    public MixedAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MixedAuthenticationException(Throwable cause) {
        super(cause);
    }
}
