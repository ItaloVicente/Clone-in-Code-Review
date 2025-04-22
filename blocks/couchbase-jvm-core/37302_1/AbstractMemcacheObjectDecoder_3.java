package io.netty.handler.codec.memcache;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheRequestDecoder;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheResponseEncoder;

public abstract class AbstractMemcacheObjectAggregator extends MessageToMessageDecoder<MemcacheObject> {

    protected FullMemcacheMessage currentMessage;

    protected ChannelHandlerContext ctx;

    public static final int DEFAULT_MAX_COMPOSITEBUFFER_COMPONENTS = 1024;

    private int maxCumulationBufferComponents = DEFAULT_MAX_COMPOSITEBUFFER_COMPONENTS;

    private final int maxContentLength;

    protected AbstractMemcacheObjectAggregator(int maxContentLength) {
        if (maxContentLength <= 0) {
            throw new IllegalArgumentException("maxContentLength must be a positive integer: " + maxContentLength);
        }

        this.maxContentLength = maxContentLength;
    }

    public final int getMaxCumulationBufferComponents() {
        return maxCumulationBufferComponents;
    }

    public final void setMaxCumulationBufferComponents(int maxCumulationBufferComponents) {
        if (maxCumulationBufferComponents < 2) {
            throw new IllegalArgumentException(
                "maxCumulationBufferComponents: " + maxCumulationBufferComponents +
                    " (expected: >= 2)");
        }

        if (ctx == null) {
            this.maxCumulationBufferComponents = maxCumulationBufferComponents;
        } else {
            throw new IllegalStateException(
                "decoder properties cannot be changed once the decoder is added to a pipeline.");
        }
    }

    public int getMaxContentLength() {
        return maxContentLength;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);

        if (currentMessage != null) {
            currentMessage.release();
            currentMessage = null;
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);

        if (currentMessage != null) {
            currentMessage.release();
            currentMessage = null;
        }
    }

}
