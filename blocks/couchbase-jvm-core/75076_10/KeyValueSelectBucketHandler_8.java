package com.couchbase.client.core.endpoint.kv;

import java.net.SocketAddress;

import com.couchbase.client.core.endpoint.ServerFeatures;
import com.couchbase.client.core.endpoint.ServerFeaturesEvent;
import com.couchbase.client.core.logging.CouchbaseLogger;
import com.couchbase.client.core.logging.CouchbaseLoggerFactory;
import com.couchbase.client.deps.io.netty.handler.codec.memcache.binary.BinaryMemcacheRequest;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import com.couchbase.client.deps.io.netty.handler.codec.memcache.binary.DefaultBinaryMemcacheRequest;
import com.couchbase.client.deps.io.netty.handler.codec.memcache.binary.FullBinaryMemcacheResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;

public class KeyValueSelectBucketHandler extends SimpleChannelInboundHandler<FullBinaryMemcacheResponse>
        implements ChannelOutboundHandler {

    private static final CouchbaseLogger LOGGER = CouchbaseLoggerFactory.getInstance(KeyValueAuthHandler.class);

    private final String bucket;

    private ChannelHandlerContext ctx;

    private ChannelPromise originalPromise;

    private boolean selectBucketEnabled;

    private static final byte SELECT_BUCKET_OPCODE = (byte)0x89;

    private static final byte SUCCESS = (byte)0x00;

    private static final byte ACCESS_ERROR = (byte)0x24;

    public KeyValueSelectBucketHandler(String bucket) {
        this.bucket = bucket;
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        if (selectBucketEnabled) {
            byte[] key = bucket.getBytes();
            short keyLength = (short) bucket.length();
            BinaryMemcacheRequest request = new DefaultBinaryMemcacheRequest(key);
            request.setOpcode(SELECT_BUCKET_OPCODE);
            request.setKeyLength(keyLength);
            request.setTotalBodyLength(keyLength);
            ctx.writeAndFlush(request);
        } else {
            originalPromise.setSuccess();
            ctx.pipeline().remove(this);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullBinaryMemcacheResponse msg) throws Exception {
        switch (msg.getStatus()) {
            case SUCCESS:
                originalPromise.setSuccess();
                ctx.pipeline().remove(this);
                ctx.fireChannelActive();
                break;
            case ACCESS_ERROR:
                originalPromise.setFailure(new AuthenticationException("Authentication Failure"));
                break;
            default:
                originalPromise.setFailure(new AuthenticationException("Unhandled select bucket status: "
                        + msg.getStatus()));
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof ServerFeaturesEvent) {
            selectBucketEnabled = ((ServerFeaturesEvent) evt).supportedFeatures().contains(ServerFeatures.SELECT_BUCKET);
        }
        super.userEventTriggered(ctx, evt);
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
