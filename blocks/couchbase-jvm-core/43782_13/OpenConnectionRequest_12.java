
package com.couchbase.client.core.message.dcp;

import io.netty.buffer.ByteBuf;

public class MutationRequest extends AbstractDCPRequest {
    private final String key;
    private final ByteBuf content;
    private final int expiration;
    private final int flags;
    private final int lockTime;
    private final long cas;

    public MutationRequest(short partition, String key, ByteBuf content, int expiration,
                           int flags, int lockTime, long cas, String bucket) {
        this(partition, key, content, expiration, flags, lockTime, cas, bucket, null);
    }

    public MutationRequest(short partition, String key, ByteBuf content, int expiration,
                           int flags, int lockTime, long cas, String bucket, String password) {
        super(bucket, password);
        this.partition(partition);
        this.key = key;
        this.content = content;
        this.expiration = expiration;
        this.flags = flags;
        this.lockTime = lockTime;
        this.cas = cas;
    }

    public String key() {
        return key;
    }

    public ByteBuf content() {
        return content;
    }

    public int expiration() {
        return expiration;
    }

    public int flags() {
        return flags;
    }

    public long cas() {
        return cas;
    }
}
