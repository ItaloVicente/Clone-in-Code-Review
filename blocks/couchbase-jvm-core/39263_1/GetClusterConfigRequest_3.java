package com.couchbase.client.core.message.binary;

public class ReplicaGetRequest extends AbstractBinaryRequest {

    private final short replica;

    public ReplicaGetRequest(String key, String bucket, short replica) {
        super(key, bucket, null);
        this.replica = replica;
    }

    public short replica() {
        return replica;
    }
}
