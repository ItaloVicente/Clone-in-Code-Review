package com.couchbase.client.core.message.binary;

import com.couchbase.client.core.message.AbstractCouchbaseResponse;
import com.couchbase.client.core.message.ResponseStatus;

public class InsertResponse extends AbstractCouchbaseResponse implements BinaryResponse {

    public InsertResponse(ResponseStatus status) {
        super(status);
    }
}
