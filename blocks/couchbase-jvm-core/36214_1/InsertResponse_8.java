package com.couchbase.client.core.message.binary;

import io.netty.buffer.ByteBuf;

public class InsertRequest extends AbstractBinaryRequest {

    private final ByteBuf content;

    private final int expiration;

    private final int flags;

    public InsertRequest(final String key, final ByteBuf content, final String bucket) {
        this(key, content, 0, 0, bucket);
    }

    public InsertRequest(final String key, final ByteBuf content, final int exp, final int flags, final String bucket) {
        super(key, bucket, null);
        this.content = content;
        this.expiration = exp;
        this.flags = flags;
    }

    public int expiration() {
        return expiration;
    }

    public int flags() {
        return flags;
    }

    public ByteBuf content() {
        return content;
    }
}
