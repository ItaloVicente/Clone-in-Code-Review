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
    }

    private BinaryMemcacheRequest createBufferAcknowledgmentRequest(int bufferBytes) {
        ByteBuf extras = lastCtx.alloc().buffer(4).writeInt(bufferBytes);
        BinaryMemcacheRequest request = new DefaultBinaryMemcacheRequest("", extras);
        request.setOpcode(DCPHandler.OP_BUFFER_ACK);
        request.setExtrasLength((byte) extras.readableBytes());
        request.setTotalBodyLength(extras.readableBytes());
        return request;
