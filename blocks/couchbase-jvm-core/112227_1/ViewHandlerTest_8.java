        try {
            ViewHandler testHandler = new ViewHandler(endpoint, responseRingBuffer, queue, false, false) {
                @Override
                public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                    super.channelRegistered(ctx);
                    ctxRef.compareAndSet(null, ctx);
                }
