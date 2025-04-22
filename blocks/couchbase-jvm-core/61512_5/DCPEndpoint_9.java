package com.couchbase.client.core.endpoint.dcp;

import com.couchbase.client.core.endpoint.kv.KeyValueStatus;
import com.couchbase.client.core.env.CoreEnvironment;
import com.couchbase.client.core.logging.CouchbaseLogger;
import com.couchbase.client.core.logging.CouchbaseLoggerFactory;
import com.couchbase.client.core.message.dcp.ConnectionType;
import com.couchbase.client.core.message.dcp.ControlParameter;
import com.couchbase.client.deps.io.netty.handler.codec.memcache.binary.BinaryMemcacheRequest;
import com.couchbase.client.deps.io.netty.handler.codec.memcache.binary.DefaultBinaryMemcacheRequest;
import com.couchbase.client.deps.io.netty.handler.codec.memcache.binary.DefaultFullBinaryMemcacheRequest;
import com.couchbase.client.deps.io.netty.handler.codec.memcache.binary.FullBinaryMemcacheRequest;
import com.couchbase.client.deps.io.netty.handler.codec.memcache.binary.FullBinaryMemcacheResponse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.net.SocketAddress;

public class DCPConnectionHandler
        extends SimpleChannelInboundHandler<FullBinaryMemcacheResponse>
        implements ChannelOutboundHandler {

    private static final CouchbaseLogger LOGGER = CouchbaseLoggerFactory.getInstance(DCPConnectionHandler.class);
    private final String connectionName;
    private final CoreEnvironment env;

    private ChannelPromise originalPromise;

    public DCPConnectionHandler(CoreEnvironment env) {
        this.connectionName = env.dcpConnectionName();
        this.env = env;
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(handleOpenConnectionRequest(ctx));
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullBinaryMemcacheResponse msg) throws Exception {
        if (msg.getOpcode() == DCPHandler.OP_OPEN_CONNECTION) {
            if (msg.getStatus() == KeyValueStatus.SUCCESS.code()) {
                if (env.dcpConnectionBufferSize() > 0) {
                    FullBinaryMemcacheRequest request = controlRequest(ctx,
                            ControlParameter.CONNECTION_BUFFER_SIZE, env.dcpConnectionBufferSize());
                    ChannelFuture future = ctx.writeAndFlush(request);
                    future.addListener(new GenericFutureListener<Future<Void>>() {
                        @Override
                        public void operationComplete(Future<Void> future) throws Exception {
                            if (!future.isSuccess()) {
                                LOGGER.warn("Error during setting CONNECTION_BUFFER_SIZE for DCP connection: {}.", future);
                            }
                        }
                    });
                } else {
                    originalPromise.setSuccess();
                    ctx.pipeline().remove(this);
                    ctx.fireChannelActive();
                }
            } else {
                originalPromise.setFailure(new IllegalStateException("Bad status for DCP Open Connection: "
                        + msg.getStatus()));
            }
        } else if (msg.getOpcode() == DCPHandler.OP_CONTROL) {
            if (msg.getStatus() == KeyValueStatus.SUCCESS.code()) {
                originalPromise.setSuccess();
                ctx.pipeline().remove(this);
                ctx.fireChannelActive();
            } else {
                originalPromise.setFailure(new IllegalStateException(
                        "Bad status for setting CONNECTION_BUFFER_SIZE DCP Open Connection: "
                                + msg.getStatus()));
            }
        }
    }

    private BinaryMemcacheRequest handleOpenConnectionRequest(final ChannelHandlerContext ctx) {
        ByteBuf extras = ctx.alloc().buffer(8);
        extras.writeInt(0) // sequence number
                .writeInt(ConnectionType.CONSUMER.flags());

        byte[] key = connectionName.getBytes(CharsetUtil.UTF_8);
        byte extrasLength = (byte) extras.readableBytes();
        short keyLength = (short) key.length;

        BinaryMemcacheRequest request = new DefaultBinaryMemcacheRequest(key, extras);
        request.setOpcode(DCPHandler.OP_OPEN_CONNECTION);
        request.setKeyLength(keyLength);
        request.setExtrasLength(extrasLength);
        request.setTotalBodyLength(keyLength + extrasLength);

        return request;
    }

    private FullBinaryMemcacheRequest controlRequest(ChannelHandlerContext ctx, ControlParameter parameter, boolean value) {
        return controlRequest(ctx, parameter, Boolean.toString(value));
    }

    private FullBinaryMemcacheRequest controlRequest(ChannelHandlerContext ctx, ControlParameter parameter, int value) {
        return controlRequest(ctx, parameter, Integer.toString(value));
    }

    private FullBinaryMemcacheRequest controlRequest(ChannelHandlerContext ctx, ControlParameter parameter, String value) {
        byte[] key = parameter.value().getBytes(CharsetUtil.UTF_8);
        short keyLength = (short) key.length;
        byte[] val = value.getBytes(CharsetUtil.UTF_8);
        ByteBuf body = ctx.alloc().buffer(val.length);
        body.writeBytes(val);

        FullBinaryMemcacheRequest request = new DefaultFullBinaryMemcacheRequest(key, Unpooled.EMPTY_BUFFER, body);
        request.setOpcode(DCPHandler.OP_CONTROL);
        request.setKeyLength(keyLength);
        request.setTotalBodyLength(keyLength + body.readableBytes());
        return request;
    }

    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        ctx.bind(localAddress, promise);
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress,
                        ChannelPromise promise) throws Exception {
        originalPromise = promise;
        ChannelPromise downPromise = ctx.newPromise();
        downPromise.addListener(new GenericFutureListener<Future<Void>>() {
            @Override
            public void operationComplete(Future<Void> future) throws Exception {
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
