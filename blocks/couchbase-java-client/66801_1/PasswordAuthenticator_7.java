package com.couchbase.client.java.auth;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public enum CredentialContext {

    BUCKET_KV, BUCKET_N1QL, CLUSTER_N1QL, CLUSTER_FTS, CLUSTER_MANAGEMENT;

}
