    private void initChannelActiveSideEffects(final ChannelHandlerContext ctx) {
        if (env().continuousKeepAliveEnabled()) {
            ctx.executor().scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    if (shouldSendKeepAlive()) {
                        createAndWriteKeepAlive(ctx);
                    }
                }
            }, 0, env().keepAliveInterval(), TimeUnit.MILLISECONDS);
        }
    }

