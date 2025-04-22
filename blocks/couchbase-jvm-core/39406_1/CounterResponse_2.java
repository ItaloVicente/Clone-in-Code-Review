package com.couchbase.client.core.message.binary;

public class CounterRequest extends AbstractBinaryRequest {

    private final long initial;
    private final long delta;
    private final int expiry;

    public CounterRequest(String key, long initial, long delta, int expiry, String bucket) {
        super(key, bucket, null);
        if (initial < 0) {
            throw new IllegalArgumentException("The initial needs to be >= 0");
        }
        this.initial = initial;
        this.delta = delta;
        this.expiry = expiry;
    }

    public long initial() {
        return initial;
    }

    public long delta() {
        return delta;
    }

    public int expiry() {
        return expiry;
    }

}
