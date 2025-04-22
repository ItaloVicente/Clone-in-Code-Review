package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;

public class DefaultBinaryMemcacheRequest extends AbstractBinaryMemcacheMessage implements BinaryMemcacheRequest {

    public static final byte REQUEST_MAGIC_BYTE = (byte) 0x80;

    private short reserved;

    public DefaultBinaryMemcacheRequest() {
        this(null, null);
    }

    public DefaultBinaryMemcacheRequest(String key) {
        this(key, null);
    }

    public DefaultBinaryMemcacheRequest(ByteBuf extras) {
        this(null, extras);
    }

    public DefaultBinaryMemcacheRequest(String key, ByteBuf extras) {
        super(key, extras);
        setMagic(REQUEST_MAGIC_BYTE);
    }

    @Override
    public short getReserved() {
        return reserved;
    }

    @Override
    public BinaryMemcacheRequest setReserved(short reserved) {
        this.reserved = reserved;
        return this;
    }

    @Override
    public BinaryMemcacheRequest retain() {
        super.retain();
        return this;
    }

    @Override
    public BinaryMemcacheRequest retain(int increment) {
        super.retain(increment);
        return this;
    }

}
