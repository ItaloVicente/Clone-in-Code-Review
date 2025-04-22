package com.couchbase.client.core.message.binary;

public class RemoveRequest extends AbstractBinaryRequest {

    private final long cas;

    public RemoveRequest(String key, String bucket) {
        this(key, 0, bucket);
    }

    public RemoveRequest(String key, long cas, String bucket) {
        super(key, bucket, null);
        this.cas = cas;
    }

    public long cas() {
        return cas;
    }

}
