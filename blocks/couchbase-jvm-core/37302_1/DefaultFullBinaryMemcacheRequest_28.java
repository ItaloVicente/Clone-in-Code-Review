package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;

public class DefaultBinaryMemcacheResponse extends AbstractBinaryMemcacheMessage implements BinaryMemcacheResponse {

    public static final byte RESPONSE_MAGIC_BYTE = (byte) 0x81;

    private short status;

    public DefaultBinaryMemcacheResponse() {
        this(null, null);
    }

    public DefaultBinaryMemcacheResponse(String key) {
        this(key, null);
    }

    public DefaultBinaryMemcacheResponse(ByteBuf extras) {
        this(null, extras);
    }

    public DefaultBinaryMemcacheResponse(String key, ByteBuf extras) {
        super(key, extras);
        setMagic(RESPONSE_MAGIC_BYTE);
    }

    @Override
    public short getStatus() {
        return status;
    }

    @Override
    public BinaryMemcacheResponse setStatus(short status) {
        this.status = status;
        return this;
    }

    @Override
    public BinaryMemcacheResponse retain() {
        super.retain();
        return this;
    }

    @Override
    public BinaryMemcacheResponse retain(int increment) {
        super.retain(increment);
        return this;
    }

}
