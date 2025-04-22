package com.couchbase.client.core.message.kv;

public class StatRequest extends AbstractKeyValueRequest {

    public StatRequest(String key, String bucket) {
        super(key, bucket, null);
    }

    @Override
    public short partition() {
        return DEFAULT_PARTITION;
    }
}
