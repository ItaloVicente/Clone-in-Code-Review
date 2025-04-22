            keepAliveThreshold++;
            if (keepAliveThreshold >= env().keepAliveErrorThreshold()) {
                LOGGER.warn(logIdent(ctx, endpoint) + "KeepAliveThreshold reached - " +
                    "closing this socket proactively.");
                ctx.close().addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (!future.isSuccess()) {
                            LOGGER.warn("Error while proactively closing the socket.", future.cause());
                        }
                    }
                });
            }
