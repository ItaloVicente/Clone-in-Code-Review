package io.netty.handler.codec.memcache;

import io.netty.buffer.ByteBufHolder;
import io.netty.channel.ChannelPipeline;

public interface MemcacheContent extends MemcacheObject, ByteBufHolder {

    @Override
    MemcacheContent copy();

    @Override
    MemcacheContent duplicate();

    @Override
    MemcacheContent retain();

    @Override
    MemcacheContent retain(int increment);

}
