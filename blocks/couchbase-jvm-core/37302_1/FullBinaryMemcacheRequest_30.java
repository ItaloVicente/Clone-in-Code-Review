package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class DefaultFullBinaryMemcacheResponse extends DefaultBinaryMemcacheResponse
    implements FullBinaryMemcacheResponse {

    private final ByteBuf content;

    public DefaultFullBinaryMemcacheResponse(String key, ByteBuf extras) {
        this(key, extras, Unpooled.buffer(0));
    }

    public DefaultFullBinaryMemcacheResponse(String key, ByteBuf extras,
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
    public FullBinaryMemcacheResponse retain() {
        content.retain();
        return this;
    }

    @Override
    public FullBinaryMemcacheResponse retain(int increment) {
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
    public FullBinaryMemcacheResponse copy() {
        return new DefaultFullBinaryMemcacheResponse(getKey(), getExtras(), content().copy());
    }

    @Override
    public FullBinaryMemcacheResponse duplicate() {
        return new DefaultFullBinaryMemcacheResponse(getKey(), getExtras(), content().duplicate());
    }
}
