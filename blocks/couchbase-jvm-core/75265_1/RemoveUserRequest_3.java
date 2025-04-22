
package com.couchbase.client.core.message.config;

import com.couchbase.client.core.message.AbstractCouchbaseRequest;

public class RemoveUserRequest extends AbstractCouchbaseRequest implements ConfigRequest {

    private final String userid;

    public RemoveUserRequest(String userid, String username, String password) {
        super(username, password);
        this.userid = userid;
    }

    @Override
    public String path() {
        return "/settings/rbac/users/builtin/" + userid;
    }
}
