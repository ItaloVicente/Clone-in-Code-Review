    private void handleSnappyCompression(final ChannelHandlerContext ctx, final BinaryMemcacheRequest request) {
        if (!(request instanceof FullBinaryMemcacheRequest)) {
            return;
        }

        FullBinaryMemcacheRequest fr = (FullBinaryMemcacheRequest) request;
        fr.setDataType(DATATYPE_SNAPPY);

        int length = fr.content().readableBytes();
        ByteBuf encoded = ctx.alloc().buffer(length);
        snappy.encode(
            fr.content(),
            encoded,
            length
        );
        fr.content().release();
        fr.setContent(encoded);
    }

    private void handleSnappyDecompression(final ChannelHandlerContext ctx, final FullBinaryMemcacheResponse response) {
        ByteBuf decoded = ctx.alloc().buffer(response.content().readableBytes());
        snappy.decode(response.content(), decoded);
        response.content().release();
        response.setContent(decoded);
    }


