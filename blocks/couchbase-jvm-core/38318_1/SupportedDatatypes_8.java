package com.couchbase.client.core.endpoint.binary;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheRequest;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheResponseStatus;
import io.netty.handler.codec.memcache.binary.DefaultFullBinaryMemcacheRequest;
import io.netty.handler.codec.memcache.binary.FullBinaryMemcacheResponse;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;

public class BinaryHelloClient extends SimpleChannelInboundHandler<FullBinaryMemcacheResponse> implements ChannelOutboundHandler {

    private static final byte HELLO_CMD = 0x1F;
    private static final byte DATATYPE_SUPPORT = 0x01;

    private static final Logger LOGGER = LoggerFactory.getLogger(BinaryHelloClient.class);

    private ChannelPromise originalPromise;

    private ChannelHandlerContext ctx;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullBinaryMemcacheResponse msg) throws Exception {
        if (msg.getStatus() == BinaryMemcacheResponseStatus.SUCCESS) {
            while(msg.content().isReadable()) {
                short supported = msg.content().readShort();
                if (supported == DATATYPE_SUPPORT) {
                    SupportedDatatypes dtypes = new SupportedDatatypes(true, true);
                    LOGGER.debug(ctx.channel().remoteAddress() + " Hello detected: " + dtypes);
                    ctx.fireUserEventTriggered(dtypes);
                }
            }
        } else {
            LOGGER.debug(ctx.channel().remoteAddress() + " Hello not successful (Response Status: "
                + msg.getStatus() + "), falling back to " + "no datatype.");
        }

        originalPromise.setSuccess();
        ctx.pipeline().remove(this);
    }

    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        ctx.bind(localAddress, promise);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }

    private void initHello() {
        ByteBuf content = ctx.alloc().buffer(2);
        content.writeShort(DATATYPE_SUPPORT);

        String key = "Couchbase Java SDK";
        BinaryMemcacheRequest request = new DefaultFullBinaryMemcacheRequest(key, Unpooled.EMPTY_BUFFER, content);
        request.setOpcode(HELLO_CMD);
        request.setTotalBodyLength(content.readableBytes() + key.length());
        request.setKeyLength((short) key.length());
        ctx.writeAndFlush(request);
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        originalPromise = promise;
        ChannelPromise downPromise = ctx.newPromise();
        downPromise.addListener(new GenericFutureListener<Future<Void>>() {
            @Override
            public void operationComplete(Future<Void> future) throws Exception {
                if (future.isSuccess()) {
                    initHello();
                }
                if (!future.isSuccess() && !originalPromise.isDone()) {
                    originalPromise.setFailure(future.cause());
                }
            }
        });
        ctx.connect(remoteAddress, localAddress, downPromise);
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        ctx.disconnect(promise);
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        ctx.close(promise);
    }

    @Override
    public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        ctx.deregister(promise);
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        ctx.read();
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ctx.write(msg, promise);
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
