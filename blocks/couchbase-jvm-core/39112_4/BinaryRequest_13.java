package com.couchbase.client.core.message.binary;

public abstract class AbstractKeyAwareBinaryRequest extends AbstractBinaryRequest {

    protected AbstractKeyAwareBinaryRequest(final String bucket, final String password) {
        super(bucket, password);
    }

    public abstract String key();

}
