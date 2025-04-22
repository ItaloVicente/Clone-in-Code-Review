
package com.couchbase.client.java.cluster;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Uncommitted
@InterfaceAudience.Public
public class UserRole {

    final String role;

    final String bucket;

    public UserRole(String role, String bucket) {
        this.role = role;
        this.bucket = bucket;
    }

    public String role() {
        return this.role;
    }

    public String bucket() {
        return this.bucket;
    }

    @Override
    public String toString() {
        return "UserRole{role:" + role + ", bucket:" + bucket + "}";

    }
}
