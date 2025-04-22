    private void channelActiveSideEffects(final ChannelHandlerContext ctx) {
        long interval = env().keepAliveInterval();
        if (env().continuousKeepAliveEnabled()) {
            ctx.executor().scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    if (shouldSendKeepAlive()) {
                        createAndWriteKeepAlive(ctx);
                    }
                }
            }, interval, interval, TimeUnit.MILLISECONDS);
        }
    }

