package com.couchbase.client.core.message.dcp;

import com.couchbase.client.core.message.CouchbaseRequest;

public interface DCPRequest extends CouchbaseRequest {
    short partition();

    DCPRequest partition(short id);
}
