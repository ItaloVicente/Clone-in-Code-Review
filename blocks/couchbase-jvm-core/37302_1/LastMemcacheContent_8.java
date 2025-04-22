package io.netty.handler.codec.memcache;

public interface FullMemcacheMessage extends MemcacheMessage, LastMemcacheContent {

    @Override
    FullMemcacheMessage copy();

    @Override
    FullMemcacheMessage retain(int increment);

    @Override
    FullMemcacheMessage retain();

    @Override
    FullMemcacheMessage duplicate();
}
