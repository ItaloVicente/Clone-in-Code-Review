    /**
     * Flush all pending writes down to the socket after finishing the authentication process.
     */
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
