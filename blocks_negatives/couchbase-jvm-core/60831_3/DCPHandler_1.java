    }

    private BinaryMemcacheRequest bufferAckRequest(ChannelHandlerContext ctx, int size) {
        ByteBuf extras = ctx.alloc().buffer(4).writeInt(size);
        BinaryMemcacheRequest request = new DefaultBinaryMemcacheRequest("", extras);
        request.setOpcode(OP_BUFFER_ACK);
        request.setExtrasLength((byte) extras.readableBytes());
        request.setTotalBodyLength(extras.readableBytes());
        return request;
