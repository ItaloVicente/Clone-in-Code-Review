package com.couchbase.client.java.auth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public class PasswordAuthenticator implements Authenticator {

    private Credential clusterManagementCredential = null;
    private Map<String, Credential> bucketCredentials = new HashMap<String, Credential>();

    @Override
    public List<Credential> getCredentials(CredentialContext context, String specific) {
        switch (context) {
            case BUCKET_KV:
            case BUCKET_N1QL:
            case BUCKET_FTS:
            case BUCKET_VIEW:
            case BUCKET_MANAGEMENT:
                return bucketCredentialOrEmpty(specific);
            case CLUSTER_MANAGEMENT:
                return clusterManagementCredential == null
                        ? Collections.<Credential>emptyList()
                        : Collections.singletonList(clusterManagementCredential);
            case CLUSTER_FTS:
            case CLUSTER_N1QL:
                return new ArrayList(bucketCredentials.values());
            default:
                throw new IllegalArgumentException("Unsupported credential context " + context + " for this Authenticator type");
        }
    }

    private List<Credential> bucketCredentialOrEmpty(String specific) {
        final Credential cred = bucketCredentials.get(specific);
        if (cred == null) {
            return Collections.emptyList();
        } else {
            return Collections.singletonList(cred);
        }
    }

    public PasswordAuthenticator bucket(String bucketName, String password) {
        this.bucketCredentials.put(bucketName, new Credential(bucketName, password));
        return this;
    }

    public PasswordAuthenticator cluster(String adminName, String adminPassword) {
        this.clusterManagementCredential = new Credential(adminName, adminPassword);
        return this;
    }
}
