    /**
     * Helper method to extract the flags from the extras buffer.
     *
     * @param ctx the handler context.
     * @param extrasReleased the extras of the msg.
     * @param extrasLength the extras length.
     * @return the extracted flags.
     */
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
    }

