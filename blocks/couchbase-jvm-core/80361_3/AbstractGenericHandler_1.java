    private void createAndWriteKeepAlive(final ChannelHandlerContext ctx) {
        CouchbaseRequest keepAlive = createKeepAliveRequest();
        if (keepAlive != null) {
            keepAlive
                .observable()
                .timeout(env().keepAliveTimeout(), TimeUnit.MILLISECONDS)
                .subscribe(new KeepAliveResponseAction(ctx));
            onKeepAliveFired(ctx, keepAlive);
