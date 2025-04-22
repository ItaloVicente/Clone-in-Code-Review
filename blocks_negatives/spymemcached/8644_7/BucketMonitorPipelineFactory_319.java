    public ChannelPipeline getPipeline() {
        ChannelPipeline pipeline = pipeline();
        pipeline.addLast("decoder", new HttpResponseDecoder());
        pipeline.addLast("encoder", new HttpRequestEncoder());
        pipeline.addLast("handler", new BucketUpdateResponseHandler());
        return pipeline;
    }
