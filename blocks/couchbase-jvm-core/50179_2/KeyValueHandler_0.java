    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            LOGGER.debug(logIdent(ctx, endpoint()) + "Identified Idle State, signalling config reload.");
            endpoint().signalConfigReload();
        }
        super.userEventTriggered(ctx, evt);
    }

