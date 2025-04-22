package com.couchbase.client.core.message.config;

import com.couchbase.client.core.message.AbstractCouchbaseResponse;
import com.couchbase.client.core.message.ResponseStatus;

public class ClusterConfigResponse extends AbstractCouchbaseResponse {

    private final String config;

    public ClusterConfigResponse(String config, ResponseStatus status) {
        super(status, null);
        this.config = config;
    }

    public String config() {
        return config;
    }

    @Override
    public String toString() {
        return "ClusterConfigResponse{" +
            "status=" + status() +
            ", config='" + config + '\'' +
            '}';
    }
}
