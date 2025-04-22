package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class DefaultFullBinaryMemcacheRequest extends DefaultBinaryMemcacheRequest
    implements FullBinaryMemcacheRequest {

    private final ByteBuf content;

    public DefaultFullBinaryMemcacheRequest(String key, ByteBuf extras) {
        this(key, extras, Unpooled.buffer(0));
    }

    public DefaultFullBinaryMemcacheRequest(String key, ByteBuf extras,
                                            ByteBuf content) {
        super(key, extras);
        if (content == null) {
            throw new NullPointerException("Supplied content is null.");
        }

        this.content = content;
    }

    @Override
    public ByteBuf content() {
        return content;
    }

    @Override
    public int refCnt() {
        return content.refCnt();
    }

    @Override
    public FullBinaryMemcacheRequest retain() {
        content.retain();
        return this;
    }

    @Override
    public FullBinaryMemcacheRequest retain(int increment) {
        content.retain(increment);
        return this;
    }

    @Override
    public boolean release() {
        return content.release();
    }

    @Override
    public boolean release(int decrement) {
        return content.release(decrement);
    }

    @Override
    public FullBinaryMemcacheRequest copy() {
        return new DefaultFullBinaryMemcacheRequest(getKey(), getExtras(), content().copy());
    }

    @Override
    public FullBinaryMemcacheRequest duplicate() {
        return new DefaultFullBinaryMemcacheRequest(getKey(), getExtras(), content().duplicate());
    }
}
