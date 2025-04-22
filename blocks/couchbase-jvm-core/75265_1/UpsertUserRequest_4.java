
package com.couchbase.client.core.message.config;

import com.couchbase.client.core.message.AbstractCouchbaseResponse;
import com.couchbase.client.core.message.ResponseStatus;

public class RemoveUserResponse extends AbstractCouchbaseResponse {
    public RemoveUserResponse(ResponseStatus status) {
        super(status, null);
    }
}
