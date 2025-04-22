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

    /**
     * Override to return a non-null request to be fired in the pipeline in case a keep alive is triggered.
     *
     * @return a CouchbaseRequest to be fired in case of keep alive (null by default).
     */
    protected CouchbaseRequest createKeepAliveRequest() {
        return null;
    }

    /**
     * Override to customize the behavior when a keep alive has been triggered and a keep alive request sent.
     *
     * The default behavior is to log the event at debug level.
     *
     * @param ctx the channel context.
     * @param keepAliveRequest the keep alive request that was sent when keep alive was triggered
     */
    protected void onKeepAliveFired(ChannelHandlerContext ctx, CouchbaseRequest keepAliveRequest) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(logIdent(ctx, endpoint) + "KeepAlive fired");
        }
    }

    /**
     * Override to customize the behavior when a keep alive has been responded to.
     *
     * The default behavior is to log the event and the response status at trace level.
     *
     * @param ctx the channel context.
     * @param keepAliveResponse the keep alive request that was sent when keep alive was triggered
     */
    protected void onKeepAliveResponse(ChannelHandlerContext ctx, CouchbaseResponse keepAliveResponse) {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(logIdent(ctx, endpoint) + "keepAlive was answered, status "
                    + keepAliveResponse.status());
        }
    }

