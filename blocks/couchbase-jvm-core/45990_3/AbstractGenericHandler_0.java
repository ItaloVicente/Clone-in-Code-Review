    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.ALL_IDLE) {
                CouchbaseRequest keepAlive = createKeepAliveRequest();
                if (keepAlive != null) {
                    keepAlive.observable().subscribe(
                            new Action1<CouchbaseResponse>() {
                                @Override
                                public void call(CouchbaseResponse couchbaseResponse) {
                                    onKeepAliveResponse(couchbaseResponse);
                                }
                            });
                    onKeepAliveFired(keepAlive);
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

    protected void onKeepAliveFired(CouchbaseRequest keepAliveRequest) {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("[][" + endpoint.getClass().getSimpleName() + "]: KeepAlive fired");
        }
    }

    protected void onKeepAliveResponse(CouchbaseResponse keepAliveResponse) {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("[][" + endpoint.getClass().getSimpleName() + "]: KeepAlive was answered, status "
                    + keepAliveResponse.status());
        }
    }

