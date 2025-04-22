
package com.couchbase.client.java.cluster;

import java.util.ArrayList;
import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

import java.util.Arrays;
import java.util.List;

@InterfaceStability.Uncommitted
@InterfaceAudience.Public
public class User {
    private String name;
    private String userId;
    private String type;
    private UserRole[] roles;

    protected void name(String name) {
        this.name = name;
    }

    protected void userId(String userId) {
        this.userId = userId;
    }

    protected void type(String type) {
        this.type = type;
    }

    protected void roles(UserRole[] roles) {
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
