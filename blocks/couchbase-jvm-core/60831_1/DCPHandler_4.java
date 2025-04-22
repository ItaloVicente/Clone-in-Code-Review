    private BinaryMemcacheRequest handleBufferAcknowledgmentRequest(ChannelHandlerContext ctx, BufferAcknowledgmentRequest msg) {
        ByteBuf extras = ctx.alloc().buffer(4).writeInt(msg.bufferBytes());
        BinaryMemcacheRequest request = new DefaultBinaryMemcacheRequest("", extras);
        request.setOpcode(OP_BUFFER_ACK);
        request.setExtrasLength((byte) extras.readableBytes());
        request.setTotalBodyLength(extras.readableBytes());
        return request;
    }

