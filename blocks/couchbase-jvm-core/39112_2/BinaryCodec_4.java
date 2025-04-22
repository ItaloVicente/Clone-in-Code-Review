    protected void decode(final ChannelHandlerContext ctx, final FullBinaryMemcacheResponse msg, final List<Object> in) throws Exception {
        ByteBuf content = msg.content().copy();
        if (msg.getDataType() == 2 || msg.getDataType() == 3) {
            ByteBuf compressed = ctx.alloc().buffer();
            snappy.decode(content, compressed);
            content.release();
            content = compressed;
        }

        int flags = 0;
        int expiration = 0;
        if (msg.getExtrasLength() > 0) {
            final ByteBuf extrasReleased = msg.getExtras();
            final ByteBuf extras = ctx.alloc().buffer(msg.getExtrasLength());
            extras.writeBytes(extrasReleased, extrasReleased.readerIndex(), extrasReleased.readableBytes());
            flags = extras.getInt(0);
            if (msg.getExtrasLength() > 4) {
                expiration = extras.getInt(1);
            }
            extras.release();
        }

        final boolean isJson = (msg.getDataType() == 0x01 || msg.getDataType() == 0x03);
        final ResponseStatus status = convertStatus(msg.getStatus());
        final CoreDocument document = new CoreDocument(msg.getKey(), content, flags, expiration, msg.getCAS(), isJson, status);
