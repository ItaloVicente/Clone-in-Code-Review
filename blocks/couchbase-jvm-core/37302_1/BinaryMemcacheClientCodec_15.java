package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.memcache.AbstractMemcacheObject;

public abstract class AbstractBinaryMemcacheMessage
    extends AbstractMemcacheObject
    implements BinaryMemcacheMessage {

    private String key;

    private ByteBuf extras;

    private byte magic;
    private byte opcode;
    private short keyLength;
    private byte extrasLength;
    private byte dataType;
    private int totalBodyLength;
    private int opaque;
    private long cas;

    protected AbstractBinaryMemcacheMessage(String key, ByteBuf extras) {
        this.key = key;
        this.extras = extras;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public ByteBuf getExtras() {
        return extras;
    }

    @Override
    public BinaryMemcacheMessage setKey(String key) {
        this.key = key;
        return this;
    }

    @Override
    public BinaryMemcacheMessage setExtras(ByteBuf extras) {
        this.extras = extras;
        return this;
    }

    @Override
    public byte getMagic() {
        return magic;
    }

    @Override
    public BinaryMemcacheMessage setMagic(byte magic) {
        this.magic = magic;
        return this;
    }

    @Override
    public long getCAS() {
        return cas;
    }

    @Override
    public BinaryMemcacheMessage setCAS(long cas) {
        this.cas = cas;
        return this;
    }

    @Override
    public int getOpaque() {
        return opaque;
    }

    @Override
    public BinaryMemcacheMessage setOpaque(int opaque) {
        this.opaque = opaque;
        return this;
    }

    @Override
    public int getTotalBodyLength() {
        return totalBodyLength;
    }

    @Override
    public BinaryMemcacheMessage setTotalBodyLength(int totalBodyLength) {
        this.totalBodyLength = totalBodyLength;
        return this;
    }

    @Override
    public byte getDataType() {
        return dataType;
    }

    @Override
    public BinaryMemcacheMessage setDataType(byte dataType) {
        this.dataType = dataType;
        return this;
    }

    @Override
    public byte getExtrasLength() {
        return extrasLength;
    }

    @Override
    public BinaryMemcacheMessage setExtrasLength(byte extrasLength) {
        this.extrasLength = extrasLength;
        return this;
    }

    @Override
    public short getKeyLength() {
        return keyLength;
    }

    @Override
    public BinaryMemcacheMessage setKeyLength(short keyLength) {
        this.keyLength = keyLength;
        return this;
    }

    @Override
    public byte getOpcode() {
        return opcode;
    }

    @Override
    public BinaryMemcacheMessage setOpcode(byte opcode) {
        this.opcode = opcode;
        return this;
    }

    @Override
    public int refCnt() {
        if (extras != null) {
            return extras.refCnt();
        }
        return 1;
    }

    @Override
    public BinaryMemcacheMessage retain() {
        if (extras != null) {
            extras.retain();
        }
        return this;
    }

    @Override
    public BinaryMemcacheMessage retain(int increment) {
        if (extras != null) {
            extras.retain(increment);
        }
        return this;
    }

    @Override
    public boolean release() {
        if (extras != null) {
            return extras.release();
        }
        return false;
    }

    @Override
    public boolean release(int decrement) {
        if (extras != null) {
            return extras.release(decrement);
        }
        return false;
    }

}
