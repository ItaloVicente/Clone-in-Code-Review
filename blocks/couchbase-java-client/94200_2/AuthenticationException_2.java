package com.couchbase.client.java.auth;

import com.couchbase.client.java.env.CouchbaseEnvironment;

import java.util.Collections;
import java.util.List;

public class CertAuthenticator implements Authenticator {

    public static CertAuthenticator INSTANCE = new CertAuthenticator();

    private static Credential CREDENTIAL = new Credential(null, null);

    private CertAuthenticator() { }

    @Override
    public List<Credential> getCredentials(CredentialContext context, String specific) {
        return Collections.singletonList(CREDENTIAL);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

}
