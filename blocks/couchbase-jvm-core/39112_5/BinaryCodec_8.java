    protected void decode(final ChannelHandlerContext ctx, final FullBinaryMemcacheResponse msg, final List<Object> in) throws Exception {
        final BinaryRequest current = queue.poll();

        ByteBuf content = msg.content().copy();
        if (msg.getDataType() == 2 || msg.getDataType() == 3) {
            ByteBuf compressed = ctx.alloc().buffer();
            snappy.decode(content, compressed);
            content.release();
            content = compressed;
