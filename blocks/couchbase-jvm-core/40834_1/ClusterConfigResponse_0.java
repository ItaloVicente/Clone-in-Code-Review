package com.couchbase.client.core.message.config;

import com.couchbase.client.core.message.AbstractCouchbaseRequest;

public class ClusterConfigRequest extends AbstractCouchbaseRequest implements ConfigRequest {

    public ClusterConfigRequest(String bucket, String password) {
        super(bucket, password);
    }

    @Override
    public String path() {
        return "/pools/default/";
    }

}
