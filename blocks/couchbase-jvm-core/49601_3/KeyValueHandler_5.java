    private static void releaseContent(ByteBuf content) {
        if (content != null && content.refCnt() > 0) {
            content.release();
        }
    }

    private static int extractFlagsFromGetResponse(ChannelHandlerContext ctx, ByteBuf extrasReleased,
        int extrasLength) {
        int flags = 0;
        if (extrasLength > 0) {
            final ByteBuf extras = ctx.alloc().buffer(extrasLength);
            extras.writeBytes(extrasReleased, extrasReleased.readerIndex(), extrasReleased.readableBytes());
            flags = extras.getInt(0);
            extras.release();
        }
        return flags;
