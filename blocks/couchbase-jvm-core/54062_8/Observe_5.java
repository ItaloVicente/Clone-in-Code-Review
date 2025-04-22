package com.couchbase.client.core.message.kv;

public class ObserveSeqnoRequest extends AbstractKeyValueRequest {

    private final long vbucketUUID;
    private final boolean master;
    private final short replica;

    public ObserveSeqnoRequest(long vbucketUUID, boolean master, short replica, String key, String bucket) {
        super(key, bucket, null);
        if (master && replica > 0) {
            throw new IllegalArgumentException("Either master or a replica node needs to be given");
        }

        this.vbucketUUID = vbucketUUID;
        this.master = master;
        this.replica = replica;
    }

    public long vbucketUUID() {
        return vbucketUUID;
    }

    public short replica() {
        return replica;
    }

    public boolean master() {
        return master;
    }

}
