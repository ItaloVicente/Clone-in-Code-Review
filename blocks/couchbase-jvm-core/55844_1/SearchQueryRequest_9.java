
package com.couchbase.client.core.message.search;

import com.couchbase.client.core.message.AbstractCouchbaseResponse;
import com.couchbase.client.core.message.ResponseStatus;

public class RemoveSearchIndexResponse extends AbstractCouchbaseResponse {

    private final String payload;

    public RemoveSearchIndexResponse(String payload, ResponseStatus status) {
        super(status, null);
        this.payload = payload;
    }

    public String payload() {
        return payload;
    }

    @Override
    public String toString() {
        return "InsertSearchIndexResponse{"
                + "status=" + status()
                + ", payload='" + payload + '\''
                + '}';
    }
}
