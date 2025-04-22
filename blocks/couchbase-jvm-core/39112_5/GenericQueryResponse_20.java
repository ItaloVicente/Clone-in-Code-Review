package com.couchbase.client.core.message.document;

import com.couchbase.client.core.message.ResponseStatus;
import io.netty.buffer.ByteBuf;

public class CoreDocument {
    private final String id;
    private final ByteBuf content;
    private final int flags;
    private final int expiration;
    private final long cas;
    private final boolean json;
    private final ResponseStatus status;

    public CoreDocument(final String id, final ByteBuf content, final int flags, final int expiration, final long cas,
                        final boolean json, final ResponseStatus status) {
        this.id = id;
        this.content = content;
        this.flags = flags;
        this.expiration = expiration;
        this.cas = cas;
        this.json = json;
        this.status = status;
    }

    public String id() {
        return id;
    }

    public ByteBuf content() {
        return content;
    }

    public int flags() {
        return flags;
    }

    public int expiration() {
        return expiration;
    }

    public long cas() {
        return cas;
    }

    public boolean isJson() {
        return json;
    }

    public ResponseStatus status() {
        return status;
    }

    @Override
    public String toString() {
        return "CoreDocument{" +
                "id='" + id + '\'' +
                ", content=" + content +
                ", flags=" + flags +
                ", expiration=" + expiration +
                ", cas=" + cas +
                ", json=" + json +
                ", status=" + status +
                '}';
    }
}
