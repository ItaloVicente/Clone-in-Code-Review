
package com.couchbase.client.java.cluster;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Uncommitted
@InterfaceAudience.Public
public class User {
    final private String name;
    final private String userId;
    final private String type;
    final private UserRole[] roles;

    protected User(String name, String userId, String type, UserRole[] roles) {
        this.name = name;
        this.userId = userId;
        this.type = type;
        this.roles = roles;
    }

    public String name() {
        return this.name;
    }

    public String userId() {
        return this.userId;
    }

    public String type() {
        return this.type;
    }

    public UserRole[] roles() {
        return this.roles;
    }
}
