    protected void createChannel() {
        ClientBootstrap bootstrap = new ClientBootstrap(factory);

        bootstrap.setPipelineFactory(new BucketMonitorPipelineFactory());

        ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port));

        channel = future.awaitUninterruptibly().getChannel();
        if (!future.isSuccess()) {
            bootstrap.releaseExternalResources();
            throw new ConnectionException("Could not connect to any pool member.");
        }
        assert(channel != null);
