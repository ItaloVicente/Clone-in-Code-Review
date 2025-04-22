    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            endpoint().signalConfigReload();
        }
        super.userEventTriggered(ctx, evt);
    }

