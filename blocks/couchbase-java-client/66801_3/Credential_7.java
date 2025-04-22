package com.couchbase.client.java.auth;

import java.util.List;
import java.util.Map;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public interface Authenticator {

    List<Credential> getCredentials(CredentialContext context, String specific);
}
