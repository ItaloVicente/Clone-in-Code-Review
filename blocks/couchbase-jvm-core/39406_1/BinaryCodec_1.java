    private BinaryMemcacheRequest handleCounterRequest(final CounterRequest request, final ChannelHandlerContext ctx) {
        ByteBuf extras = ctx.alloc().buffer();
        extras.writeLong(Math.abs(request.delta()));
        extras.writeLong(request.initial());
        extras.writeInt(request.expiry());

        BinaryMemcacheRequest msg = new DefaultBinaryMemcacheRequest(request.key(), extras);
        msg.setOpcode(request.delta() < 0 ? (byte) 0x06 : (byte) 0x05);
        msg.setKeyLength((short) request.key().length());
        msg.setTotalBodyLength((short) request.key().length() + extras.readableBytes());
        msg.setExtrasLength((byte) extras.readableBytes());

        return msg;
    }

