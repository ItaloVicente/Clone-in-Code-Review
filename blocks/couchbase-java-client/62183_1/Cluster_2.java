
package com.couchbase.client.java;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public enum AuthenticationContext {

    BUCKET_KEYVALUE("bucket-kv"),
    BUCKET_N1QL("bucket-n1ql"),
    CLUSTER_N1QL("cluster-n1ql"),
    CLUSTER_FTS("cluster-cbft"),
    CLUSTER_MANAGEMENT("cluster-mgmt");

    private final String sdkCompatibleRepresentation;

    AuthenticationContext(String sdkCompatibleRepresentation) {
        this.sdkCompatibleRepresentation = sdkCompatibleRepresentation;
    }

    public String getSdkCompatibleRepresentation() {
        return sdkCompatibleRepresentation;
    }
}
