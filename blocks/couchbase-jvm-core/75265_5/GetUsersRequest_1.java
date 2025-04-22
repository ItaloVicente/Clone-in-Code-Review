
package com.couchbase.client.core.message.config;

import com.couchbase.client.core.message.AbstractCouchbaseRequest;

public class GetUsersRequest extends AbstractCouchbaseRequest implements ConfigRequest {

    public GetUsersRequest(String username, String password) {
        super(username, password);
    }

    public String path() {
        return "/settings/rbac/users";
    }
}
