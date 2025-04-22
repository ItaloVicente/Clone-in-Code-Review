package com.couchbase.client.java.auth;

import java.util.Collections;
import java.util.List;
import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Uncommitted
@InterfaceAudience.Public
public class PasswordAuthenticator implements Authenticator {
    final private String username;
    final private String password;

    public PasswordAuthenticator(String password) {
        this.username = null;
        this.password = password;
    }

    public PasswordAuthenticator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public List<Credential> getCredentials(CredentialContext context, String specific) {
        return Collections.singletonList(new Credential(username, password));
    }

    public boolean isEmpty() {
        return false;
    }

    public String username() {
        return this.username;
    }

    public String password() {
        return this.password;
    }
}
