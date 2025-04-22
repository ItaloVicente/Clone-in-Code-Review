package com.couchbase.client.java.error;

import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.java.auth.Authenticator;
import com.couchbase.client.java.auth.CredentialContext;

public class AuthenticatorException extends CouchbaseException {

    private final CredentialContext context;
    private final String specific;
    private final int foundCredentials;

    public AuthenticatorException(String message, CredentialContext context, String specific, int found) {
        super(message + " [" + context + "/" + specific + ", " + found + " found]");
        this.context = context;
        this.specific = specific;
        this.foundCredentials = found;
    }

    public CredentialContext context() {
        return context;
    }

    public String specific() {
        return specific;
    }

    public int foundCredentials() {
        return 0;
    }
}
