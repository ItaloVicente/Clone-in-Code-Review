
    @Override
    public void userEventTriggered(final ChannelHandlerContext ctx, final Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            LOGGER.debug("Config Handler Idle State Event triggered, closing the socket.");
            ctx.close().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (!future.isSuccess()) {
                        LOGGER.warn("Error while proactively closing the socket.", future.cause());
                    }
                }
            });
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
