package com.couchbase.client.core.message.binary;


import io.netty.buffer.ByteBuf;

public class ReplaceRequest extends AbstractBinaryRequest {

    private final ByteBuf content;

    private final int expiration;

    private final int flags;

    private final long cas;

    public ReplaceRequest(final String key, final ByteBuf content, final String bucket) {
        this(key, content, 0, 0, 0, bucket);
    }

    public ReplaceRequest(final String key, final ByteBuf content, final long cas, final String bucket) {
        this(key, content, cas, 0, 0, bucket);
    }

    public ReplaceRequest(final String key, final ByteBuf content, final long cas, final int exp, final int flags, final String bucket) {
        super(key, bucket, null);
        this.content = content;
        this.expiration = exp;
        this.flags = flags;
        this.cas = cas;
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

    public long cas() {
        return cas;
    }
}
