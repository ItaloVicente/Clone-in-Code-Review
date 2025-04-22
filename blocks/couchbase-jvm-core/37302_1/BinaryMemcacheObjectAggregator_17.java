package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.memcache.MemcacheMessage;

public interface BinaryMemcacheMessage extends MemcacheMessage {

    byte getMagic();

    BinaryMemcacheMessage setMagic(byte magic);

    byte getOpcode();

    BinaryMemcacheMessage setOpcode(byte code);

    short getKeyLength();

    BinaryMemcacheMessage setKeyLength(short keyLength);

    byte getExtrasLength();

    BinaryMemcacheMessage setExtrasLength(byte extrasLength);

    byte getDataType();

    BinaryMemcacheMessage setDataType(byte dataType);

    int getTotalBodyLength();

    BinaryMemcacheMessage setTotalBodyLength(int totalBodyLength);

    int getOpaque();

    BinaryMemcacheMessage setOpaque(int opaque);

    long getCAS();

    BinaryMemcacheMessage setCAS(long cas);

    String getKey();

    BinaryMemcacheMessage setKey(String key);

    ByteBuf getExtras();

    BinaryMemcacheMessage setExtras(ByteBuf extras);

    @Override
    BinaryMemcacheMessage retain();

    @Override
    BinaryMemcacheMessage retain(int increment);

}
