package com.couchbase.client.java.auth;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CertAuthenticatorTest {

    @Test
    public void getCredentials() {
        List<Credential> creds = CertAuthenticator.INSTANCE.getCredentials(CredentialContext.BUCKET_KV, null);
        assertEquals(1, creds.size());
        assertNull(creds.get(0).login());
        assertNull(creds.get(0).password());
    }

    @Test
    public void isEmpty() {
        assertFalse(CertAuthenticator.INSTANCE.isEmpty());
    }
}
