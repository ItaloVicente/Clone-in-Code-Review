        updateConnectionStats(ctx, connection, msg);
    }

    private void updateConnectionStats(final ChannelHandlerContext ctx, final DCPConnection connection, final FullBinaryMemcacheResponse msg) {
        connection.inc(msg.getTotalBodyLength());
        if (connection.totalReceivedBytes() >= env().dcpConnectionBufferSize() * env().dcpConnectionBufferAckThreshold()) {
            ctx.writeAndFlush(bufferAckRequest(ctx, connection.totalReceivedBytes()));
            connection.reset();
        }
    }

    private BinaryMemcacheRequest bufferAckRequest(ChannelHandlerContext ctx, int size) {
        ByteBuf extras = ctx.alloc().buffer(4).writeInt(size);
        BinaryMemcacheRequest request = new DefaultBinaryMemcacheRequest("", extras);
        request.setOpcode(OP_BUFFER_ACK);
        request.setExtrasLength((byte) extras.readableBytes());
        request.setTotalBodyLength(extras.readableBytes());
        return request;
    }

    private FullBinaryMemcacheRequest controlRequest(ChannelHandlerContext ctx, ControlParameter parameter, boolean value) {
        return controlRequest(ctx, parameter, Boolean.toString(value));
    }

    private FullBinaryMemcacheRequest controlRequest(ChannelHandlerContext ctx, ControlParameter parameter, int value) {
        return controlRequest(ctx, parameter, Integer.toString(value));
    }

    private FullBinaryMemcacheRequest controlRequest(ChannelHandlerContext ctx, ControlParameter parameter, String value) {
        String key = parameter.value();
        short keyLength = (short) key.getBytes(CharsetUtil.UTF_8).length;
        byte[] val = value.getBytes(CharsetUtil.UTF_8);
        ByteBuf body = ctx.alloc().buffer(val.length);
        body.writeBytes(val);

        FullBinaryMemcacheRequest request = new DefaultFullBinaryMemcacheRequest(key, Unpooled.EMPTY_BUFFER, body);
        request.setOpcode(OP_CONTROL);
        request.setKeyLength(keyLength);
        request.setTotalBodyLength(keyLength + body.readableBytes());
        return request;
