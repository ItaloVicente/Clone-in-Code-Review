        connection.subject().onCompleted();
        super.handlerRemoved(ctx);
    }

    private void updateConnectionStats(final ChannelHandlerContext ctx, final DCPConnection connection, final FullBinaryMemcacheResponse msg) {
        connection.inc(msg.getTotalBodyLength());
        if (connection.totalReceivedBytes() >= env().dcpConnectionBufferSize() * env().dcpConnectionBufferAckThreshold()) {
            ctx.writeAndFlush(bufferAckRequest(ctx, connection.totalReceivedBytes()));
            connection.reset();
