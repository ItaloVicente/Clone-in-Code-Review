package io.netty.handler.codec.memcache.binary;

public interface BinaryMemcacheRequest extends BinaryMemcacheMessage {

    short getReserved();

    BinaryMemcacheRequest setReserved(short reserved);

    @Override
    BinaryMemcacheRequest retain();

    @Override
    BinaryMemcacheRequest retain(int increment);

}
