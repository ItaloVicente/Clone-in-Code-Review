        try {
            QueryHandlerV2 testHandler = new QueryHandlerV2(endpoint, responseRingBuffer, queue, false, false) {
                @Override
                public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                    super.channelRegistered(ctx);
                    ctxRef.compareAndSet(null, ctx);
                }
