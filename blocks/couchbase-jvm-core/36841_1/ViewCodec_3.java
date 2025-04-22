package com.couchbase.client.core.endpoint.binary;

import com.couchbase.client.core.endpoint.AbstractEndpoint;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheResponseStatus;
import io.netty.handler.codec.memcache.binary.DefaultBinaryMemcacheRequest;
import io.netty.handler.codec.memcache.binary.DefaultFullBinaryMemcacheRequest;
import io.netty.handler.codec.memcache.binary.FullBinaryMemcacheRequest;
import io.netty.handler.codec.memcache.binary.FullBinaryMemcacheResponse;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.PendingWrite;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.sasl.Sasl;
import javax.security.sasl.SaslClient;
import java.io.IOException;
import java.net.SocketAddress;
import java.util.ArrayDeque;
import java.util.Deque;

public class BinarySaslClient
    extends SimpleChannelInboundHandler<FullBinaryMemcacheResponse>
    implements ChannelOutboundHandler, CallbackHandler {

    private static final byte SASL_LIST_MECHS_OPCODE = 0x20;

    private static final byte SASL_AUTH_OPCODE = 0x21;

    private static final byte SASL_STEP_OPCODE = 0x22;

    private static final byte SASL_AUTH_SUCCESS = BinaryMemcacheResponseStatus.SUCCESS;

    private static final byte SASL_AUTH_FAILURE = BinaryMemcacheResponseStatus.AUTH_ERROR;

    private final String username;

    private final String password;

    private final AbstractEndpoint endpoint;

    private final Deque<PendingWrite> pendingWrites = new ArrayDeque<PendingWrite>();

    private ChannelHandlerContext ctx;

    private SaslClient saslClient;

    private String selectedMechanism;

    public BinarySaslClient(String username, String password, AbstractEndpoint endpoint) {
        this.username = username;
        this.password = password;
        this.endpoint = endpoint;
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        negotiate();
    }

    private void negotiate() {
        ctx.writeAndFlush(new DefaultBinaryMemcacheRequest().setOpcode(SASL_LIST_MECHS_OPCODE));
    }

    @Override
    public void handle(final Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (Callback callback : callbacks) {
            if (callback instanceof NameCallback) {
                ((NameCallback) callback).setName(username);
            } else if (callback instanceof PasswordCallback) {
                ((PasswordCallback) callback).setPassword(password.toCharArray());
            } else {
                throw new IllegalStateException("SASLClient requested unsupported callback: " + callback);
            }
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullBinaryMemcacheResponse msg) throws Exception {
        if (msg.getOpcode() == SASL_LIST_MECHS_OPCODE) {
            handleListMechsResponse(ctx, msg);
        } else if (msg.getOpcode() == SASL_AUTH_OPCODE) {
            handleAuthResponse(ctx, msg);
        } else if (msg.getOpcode() == SASL_STEP_OPCODE) {
            checkIsAuthed(msg);
        }
    }

    private void handleListMechsResponse(ChannelHandlerContext ctx, FullBinaryMemcacheResponse msg) throws Exception {
        String remote = ctx.channel().remoteAddress().toString();
        String[] supportedMechanisms = msg.content().toString(CharsetUtil.UTF_8).split(" ");
        if (supportedMechanisms.length == 0) {
            throw new IllegalStateException("Received empty SASL mechanisms list from server: " + remote);
        }

        saslClient = Sasl.createSaslClient(supportedMechanisms, null, "couchbase", remote, null, this);
        selectedMechanism = saslClient.getMechanismName();
        int mechanismLength = selectedMechanism.length();
        byte[] bytePayload = saslClient.hasInitialResponse() ? saslClient.evaluateChallenge(new byte[]{}) : null;
        ByteBuf payload = bytePayload != null ? ctx.alloc().buffer().writeBytes(bytePayload) : Unpooled.EMPTY_BUFFER;

        FullBinaryMemcacheRequest initialRequest = new DefaultFullBinaryMemcacheRequest(
            selectedMechanism,
            Unpooled.EMPTY_BUFFER,
            payload
        );
        initialRequest
            .setOpcode(SASL_AUTH_OPCODE)
            .setKeyLength((short) mechanismLength)
            .setTotalBodyLength(mechanismLength + payload.readableBytes());

        ctx.writeAndFlush(initialRequest);
    }

    private void handleAuthResponse(ChannelHandlerContext ctx, FullBinaryMemcacheResponse msg) throws Exception {
        if (saslClient.isComplete()) {
            checkIsAuthed(msg);
            return;
        }

        byte[] response = new byte[msg.content().readableBytes()];
        msg.content().readBytes(response);
        byte[] evaluatedBytes = saslClient.evaluateChallenge(response);

        if (evaluatedBytes != null) {
            String[] evaluated = new String(evaluatedBytes).split(" ");
            ByteBuf content = Unpooled.copiedBuffer(username + "\0" + evaluated[1], CharsetUtil.UTF_8);

            FullBinaryMemcacheRequest stepRequest = new DefaultFullBinaryMemcacheRequest(
                selectedMechanism,
                Unpooled.EMPTY_BUFFER,
                content
            );
            stepRequest
                .setOpcode(SASL_STEP_OPCODE)
                .setKeyLength((short) selectedMechanism.length())
                .setTotalBodyLength(content.readableBytes() + selectedMechanism.length());

            ctx.writeAndFlush(stepRequest);
        } else {
            throw new IllegalStateException("SASL Challenge evaluation returned null.");
        }
    }

    private void checkIsAuthed(final FullBinaryMemcacheResponse msg) {
        switch (msg.getStatus()) {
            case SASL_AUTH_SUCCESS:
                endpoint.notifyChannelAuthSuccess();
                flushPendingWrites();
                break;
            case SASL_AUTH_FAILURE:
                endpoint.notifyChannelAuthFailure();
                failPendingWrites();
                break;
            default:
                throw new IllegalStateException("Unhandled SASL auth status: " + msg.getStatus());
        }
    }

    private void flushPendingWrites() {
        for(;;) {
            PendingWrite write = pendingWrites.poll();
            if (write == null) {
                break;
            }
            ctx.write(write.msg(), (ChannelPromise) write.recycleAndGet());
        }
        ctx.flush();
    }

    private void failPendingWrites() {
        throw new UnsupportedOperationException("Implemet me properly");
    }

    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        ctx.bind(localAddress, promise);
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        ctx.connect(remoteAddress, localAddress, promise);
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
        pendingWrites.add(PendingWrite.newInstance(msg, promise));
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
