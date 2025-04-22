    private FullBinaryMemcacheRequest controlRequest(ChannelHandlerContext ctx, ControlParameter parameter, boolean value) {
        return controlRequest(ctx, parameter, Boolean.toString(value));
    }

    private FullBinaryMemcacheRequest controlRequest(ChannelHandlerContext ctx, ControlParameter parameter, int value) {
        return controlRequest(ctx, parameter, Integer.toString(value));
    }

    private FullBinaryMemcacheRequest controlRequest(ChannelHandlerContext ctx, ControlParameter parameter, String value) {
        byte[] key = parameter.value().getBytes(CharsetUtil.UTF_8);
        short keyLength = (short) key.length;
        byte[] val = value.getBytes(CharsetUtil.UTF_8);
        ByteBuf body = ctx.alloc().buffer(val.length);
        body.writeBytes(val);

        FullBinaryMemcacheRequest request = new DefaultFullBinaryMemcacheRequest(key, Unpooled.EMPTY_BUFFER, body);
        request.setOpcode(OP_CONTROL);
        request.setKeyLength(keyLength);
        request.setTotalBodyLength(keyLength + body.readableBytes());
        return request;
    }

    /**
     * Creates a DCP Open Connection Request.
     *
     * @param ctx the channel handler context.
     * @param msg the open connect message.
     * @return a converted {@link BinaryMemcacheRequest}.
     */
    private BinaryMemcacheRequest handleOpenConnectionRequest(final ChannelHandlerContext ctx,
                                                              final OpenConnectionRequest msg) {
        ByteBuf extras = ctx.alloc().buffer(8);
        extras
                .writeInt(msg.sequenceNumber())
                .writeInt(msg.type().flags());

        byte[] key = msg.connectionName().getBytes(CharsetUtil.UTF_8);
        byte extrasLength = (byte) extras.readableBytes();
        short keyLength = (short) key.length;

        BinaryMemcacheRequest request = new DefaultBinaryMemcacheRequest(key, extras);
        request.setOpcode(OP_OPEN_CONNECTION);
        request.setKeyLength(keyLength);
        request.setExtrasLength(extrasLength);
        request.setTotalBodyLength(keyLength + extrasLength);

        return request;
    }
