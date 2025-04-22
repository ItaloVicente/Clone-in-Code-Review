    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress,
        ChannelPromise future) throws Exception {
        connectFuture = future;
        ctx.connect(remoteAddress, localAddress, future);
    }

