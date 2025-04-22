    @Override
    public void userEventTriggered(final ChannelHandlerContext ctx, final Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            ctx.channel().writeAndFlush(new NoopRequest(null, bucket, password));
        }
        ctx.fireUserEventTriggered(evt);
    }
