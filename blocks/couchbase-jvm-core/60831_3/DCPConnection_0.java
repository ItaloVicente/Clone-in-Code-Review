    public void consumed(final DCPMessage event) {
        consumed(event.totalBodyLength());
    }

    public void consumed(final FullBinaryMemcacheResponse response) {
        consumed(response.getTotalBodyLength());
    }

    private void consumed(int delta) {
        if (env.dcpConnectionBufferSize() > 0) {
            totalReceivedBytes += MINIMUM_HEADER_SIZE + delta;
            if (totalReceivedBytes >= env.dcpConnectionBufferSize() * env.dcpConnectionBufferAckThreshold()) {
                lastCtx.writeAndFlush(createBufferAcknowledgmentRequest(totalReceivedBytes));
                totalReceivedBytes = 0;
            }
        }
