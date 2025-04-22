package com.couchbase.client.core.message.cluster;

import com.couchbase.client.core.message.AbstractCouchbaseRequest;

public class CloseBucketRequest extends AbstractCouchbaseRequest implements ClusterRequest {

    public CloseBucketRequest(String bucket) {
        super(bucket, null);
    }

}
