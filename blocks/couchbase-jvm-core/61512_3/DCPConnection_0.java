    private void consumed(int streamId, int delta) {
        ChannelHandlerContext ctx = streams.get(streamId);
        if (env.dcpConnectionBufferSize() > 0 && ctx != null) {
            Attribute<Integer> attr = ctx.attr(DCPHandler.RECEIVED_BYTES);

            synchronized (ctx) {
                int receivedBytes = attr.get();
                receivedBytes += MINIMUM_HEADER_SIZE + delta;
                if (receivedBytes >= env.dcpConnectionBufferSize() * env.dcpConnectionBufferAckThreshold()) {
                    ctx.writeAndFlush(createBufferAcknowledgmentRequest(ctx, receivedBytes));
                    receivedBytes = 0;
                }
                attr.set(receivedBytes);
