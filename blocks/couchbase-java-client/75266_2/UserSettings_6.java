
package com.couchbase.client.java.cluster;

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
}
