    @Override
    public void channelWritabilityChanged(final ChannelHandlerContext ctx) throws Exception {
        if (!ctx.channel().isWritable()) {
            ctx.flush();
        }
        ctx.fireChannelWritabilityChanged();
    }
