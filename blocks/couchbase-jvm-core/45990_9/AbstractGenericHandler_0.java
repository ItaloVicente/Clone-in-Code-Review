    @Override
    public void userEventTriggered(final ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.ALL_IDLE) {
                CouchbaseRequest keepAlive = createKeepAliveRequest();
                if (keepAlive != null) {
                    keepAlive.observable().subscribe(new KeepAliveResponseAction(ctx));
                    onKeepAliveFired(ctx, keepAlive);
                    ctx.pipeline().writeAndFlush(keepAlive);
                }
                return;
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    protected CouchbaseRequest createKeepAliveRequest() {
        return null;
    }

    protected void onKeepAliveFired(ChannelHandlerContext ctx, CouchbaseRequest keepAliveRequest) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(logIdent(ctx, endpoint) + "KeepAlive fired");
        }
    }

    protected void onKeepAliveResponse(ChannelHandlerContext ctx, CouchbaseResponse keepAliveResponse) {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(logIdent(ctx, endpoint) + "keepAlive was answered, status "
                    + keepAliveResponse.status());
        }
    }

