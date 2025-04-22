    private static BinaryMemcacheRequest handleObserveSeqnoRequest(final ChannelHandlerContext ctx,
        final ObserveSeqnoRequest msg) {
        ByteBuf content = ctx.alloc().buffer();
        content.writeLong(msg.vbucketUUID());

        BinaryMemcacheRequest request = new DefaultFullBinaryMemcacheRequest("", Unpooled.EMPTY_BUFFER, content);
        request.setOpcode(OP_OBSERVE_SEQ);
        request.setTotalBodyLength(content.readableBytes());
        return request;
    }

