
package com.couchbase.client.core.message.config;

import com.couchbase.client.core.message.AbstractCouchbaseRequest;

public class UpsertUserRequest extends AbstractCouchbaseRequest implements ConfigRequest {

    private final String userid;
    private final String payload;

    public UpsertUserRequest(String userid, String payload, String username, String password) {
        super(username, password);
        this.userid = userid;
        this.payload = payload;
    }

    @Override
    public String path() {
        return "/settings/rbac/users/builtin/" + userid;
    }

    public String payload() {
        return payload;
    }
}
