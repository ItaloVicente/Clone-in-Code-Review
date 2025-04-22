package com.couchbase.client.core.message.binary;

import com.couchbase.client.core.message.AbstractCouchbaseRequest;

public abstract class AbstractBinaryRequest extends AbstractCouchbaseRequest implements BinaryRequest {

    private final String key;

    private short partition = -1;

    protected AbstractBinaryRequest(String key, String bucket, String password) {
        super(bucket, password);
        this.key = key;
    }

    @Override
    public String key() {
        return key;
    }

    @Override
    public short partition() {
        if (partition == -1) {
            throw new IllegalStateException("Partition requested but not set beforehand");
        }
        return partition;
    }

    @Override
    public BinaryRequest partition(short partition) {
        if (partition < 0) {
            throw new IllegalArgumentException("Partition must be larger than or equal to zero");
        }
        this.partition = partition;
        return this;
    }
}
