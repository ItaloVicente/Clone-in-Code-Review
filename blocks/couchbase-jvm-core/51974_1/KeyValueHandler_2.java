package com.couchbase.client.core.endpoint.kv;

import com.couchbase.client.core.endpoint.ResponseStatusConverter;
import com.couchbase.client.core.endpoint.ServerFeatures;
import com.couchbase.client.core.endpoint.ServerFeaturesEvent;
import com.couchbase.client.core.env.CoreEnvironment;
import com.couchbase.client.core.logging.CouchbaseLogger;
import com.couchbase.client.core.logging.CouchbaseLoggerFactory;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.deps.io.netty.handler.codec.memcache.binary.DefaultFullBinaryMemcacheRequest;
import com.couchbase.client.deps.io.netty.handler.codec.memcache.binary.FullBinaryMemcacheRequest;
import com.couchbase.client.deps.io.netty.handler.codec.memcache.binary.FullBinaryMemcacheResponse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KeyValueFeatureHandler extends SimpleChannelInboundHandler<FullBinaryMemcacheResponse>
    implements ChannelOutboundHandler {

    private static final CouchbaseLogger LOGGER = CouchbaseLoggerFactory.getInstance(KeyValueFeatureHandler.class);
    private static final byte HELLO_CMD = 0x1f;

    private static final ServerFeatures[] FEATURES = {
        ServerFeatures.MUTATION_SEQNO,
        ServerFeatures.TCPNODELAY
    };

    private ChannelPromise originalPromise;

    private final CoreEnvironment environment;

    public KeyValueFeatureHandler(CoreEnvironment environment) {
        this.environment = environment;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullBinaryMemcacheResponse msg) throws Exception {
        List<ServerFeatures> supported = new ArrayList<ServerFeatures>();

        ResponseStatus responseStatus = ResponseStatusConverter.fromBinary(msg.getStatus());
        if (responseStatus.isSuccess()) {
            while (msg.content().isReadable()) {
                supported.add(ServerFeatures.fromValue(msg.content().readShort()));
            }
        } else {
            LOGGER.debug("HELLO Negotiation did not succeed ({}).", responseStatus);
        }

        LOGGER.debug("Negotiated supported features: {}", supported);
        ctx.fireUserEventTriggered(new ServerFeaturesEvent(supported));
        originalPromise.setSuccess();
        ctx.pipeline().remove(this);
        ctx.fireChannelActive();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
       ctx.writeAndFlush(helloRequest());
    }

    private FullBinaryMemcacheRequest helloRequest() {
        String key = environment.userAgent();
        short keyLength = (short) key.getBytes(CharsetUtil.UTF_8).length;

        ByteBuf wanted = Unpooled.buffer(FEATURES.length * 2);
        for (ServerFeatures feature : FEATURES) {
            wanted.writeShort(feature.value());
        }

        LOGGER.debug("Requesting supported features: {}", Arrays.asList(FEATURES));
        FullBinaryMemcacheRequest request = new DefaultFullBinaryMemcacheRequest(key, Unpooled.EMPTY_BUFFER, wanted);
        request.setOpcode(HELLO_CMD);
        request.setKeyLength(keyLength);
        request.setTotalBodyLength(keyLength + wanted.readableBytes());
        return request;
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

    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        ctx.bind(localAddress, promise);
    }
}
