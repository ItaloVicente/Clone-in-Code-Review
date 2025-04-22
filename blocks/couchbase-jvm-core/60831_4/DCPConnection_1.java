    private void consumed(int delta) {
        if (env.dcpConnectionBufferSize() > 0) {
            totalReceivedBytes += MINIMUM_HEADER_SIZE + delta;
            if (totalReceivedBytes >= env.dcpConnectionBufferSize() * env.dcpConnectionBufferAckThreshold()) {
                lastCtx.writeAndFlush(createBufferAcknowledgmentRequest(totalReceivedBytes));
                totalReceivedBytes = 0;
            }
        }
    }

    public void setLastContext(ChannelHandlerContext ctx) {
        lastCtx = ctx;
