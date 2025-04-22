        FullBinaryMemcacheRequest msg = new DefaultFullBinaryMemcacheRequest(request.key(), extras, request.content());

        msg.setOpcode(BinaryMemcacheOpcodes.REPLACE);
        msg.setCAS(request.cas());
        msg.setKeyLength((short) request.key().length());
        msg.setTotalBodyLength((short) request.key().length() + request.content().readableBytes() + extras.readableBytes());
        msg.setReserved(request.partition());
        msg.setExtrasLength((byte) extras.readableBytes());
        return msg;
    }

    private BinaryMemcacheRequest handleInsertRequest(final InsertRequest request, final ChannelHandlerContext ctx) {
        ByteBuf extras = ctx.alloc().buffer(8);
        extras.writeInt(request.flags());
        extras.writeInt(request.expiration());

        FullBinaryMemcacheRequest msg = new DefaultFullBinaryMemcacheRequest(request.key(), extras, request.content());

        msg.setOpcode(BinaryMemcacheOpcodes.ADD);
        msg.setKeyLength((short) request.key().length());
        msg.setTotalBodyLength((short) request.key().length() + request.content().readableBytes() + extras.readableBytes());
        msg.setReserved(request.partition());
        msg.setExtrasLength((byte) extras.readableBytes());
