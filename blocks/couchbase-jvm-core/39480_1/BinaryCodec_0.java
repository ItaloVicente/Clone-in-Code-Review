    private BinaryMemcacheRequest handleObserveRequest(final ObserveRequest request, final ChannelHandlerContext ctx) {
        ByteBuf content = ctx.alloc().buffer();
        content.writeShort(request.partition());
        content.writeShort(request.key().length());
        content.writeBytes(request.key().getBytes(CharsetUtil.UTF_8));

        BinaryMemcacheRequest msg = new DefaultFullBinaryMemcacheRequest("", Unpooled.EMPTY_BUFFER, content);

        msg.setOpcode((byte) 0x92);
        msg.setTotalBodyLength(content.readableBytes());
        return msg;
    }

