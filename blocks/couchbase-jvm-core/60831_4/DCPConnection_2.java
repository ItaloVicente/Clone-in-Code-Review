    private BinaryMemcacheRequest createBufferAcknowledgmentRequest(int bufferBytes) {
        ByteBuf extras = lastCtx.alloc().buffer(4).writeInt(bufferBytes);
        BinaryMemcacheRequest request = new DefaultBinaryMemcacheRequest("", extras);
        request.setOpcode(DCPHandler.OP_BUFFER_ACK);
        request.setExtrasLength((byte) extras.readableBytes());
        request.setTotalBodyLength(extras.readableBytes());
        return request;
