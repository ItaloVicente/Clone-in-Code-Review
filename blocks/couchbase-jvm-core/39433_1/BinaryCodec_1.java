    private BinaryMemcacheRequest handleUnlockRequest(final UnlockRequest request, final ChannelHandlerContext ctx) {
        BinaryMemcacheRequest msg = new DefaultBinaryMemcacheRequest(request.key());
        msg.setOpcode((byte) 0x95);
        msg.setKeyLength((short) request.key().length());
        msg.setTotalBodyLength(request.key().length());
        msg.setCAS(request.cas());
        msg.setReserved(request.partition());
        return msg;
    }

    private BinaryMemcacheRequest handleTouchRequest(final TouchRequest request, final ChannelHandlerContext ctx) {
        ByteBuf extras = ctx.alloc().buffer();
        extras.writeInt(request.expiry());

        BinaryMemcacheRequest msg = new DefaultBinaryMemcacheRequest(request.key());
        msg.setExtras(extras);
        msg.setOpcode((byte) 0x1c);
        msg.setKeyLength((short) request.key().length());
        msg.setTotalBodyLength(request.key().length() + extras.readableBytes());
        msg.setExtrasLength((byte) extras.readableBytes());
        msg.setReserved(request.partition());
        return msg;
    }

