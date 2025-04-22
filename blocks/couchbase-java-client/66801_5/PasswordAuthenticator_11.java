package com.couchbase.client.java.auth;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public enum CredentialContext {

    BUCKET_KV, BUCKET_VIEW, BUCKET_N1QL, BUCKET_FTS,
    CLUSTER_N1QL, CLUSTER_FTS,
    BUCKET_MANAGEMENT, CLUSTER_MANAGEMENT;

}
