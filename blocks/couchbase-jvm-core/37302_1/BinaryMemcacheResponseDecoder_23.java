package io.netty.handler.codec.memcache.binary;

public interface BinaryMemcacheResponse extends BinaryMemcacheMessage {

    short getStatus();

    BinaryMemcacheResponse setStatus(short status);

    @Override
    BinaryMemcacheResponse retain();

    @Override
    BinaryMemcacheResponse retain(int increment);

}
