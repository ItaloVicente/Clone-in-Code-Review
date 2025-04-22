    private void handleSnappyCompression(final ChannelHandlerContext ctx, final BinaryMemcacheRequest r) {
        if (!(r instanceof FullBinaryMemcacheRequest)) {
            return;
        }

        FullBinaryMemcacheRequest request = (FullBinaryMemcacheRequest) r;

        int uncompressedLength = request.content().readableBytes();
        ByteBuf compressedContent = ctx.alloc().buffer(uncompressedLength);
        snappy.encode(
            request.content().slice(),
            compressedContent,
            uncompressedLength
        );

        if (compressedContent.readableBytes() >= uncompressedLength) {
            compressedContent.release();
        } else {
            request.setDataType(DATATYPE_SNAPPY);
            request.content().release();
            request.setContent(compressedContent);
            request.setTotalBodyLength(
                request.getExtrasLength()
                    + request.getKeyLength()
                    + compressedContent.readableBytes()
            );
        }
    }

    private void handleSnappyDecompression(final ChannelHandlerContext ctx, final FullBinaryMemcacheResponse response) {
        ByteBuf decompressed = ctx.alloc().buffer(response.content().readableBytes());
        snappy.decode(response.content(), decompressed);
        response.content().release();
        response.setContent(decompressed);
        response.setTotalBodyLength(
            response.getExtrasLength()
                + response.getKeyLength()
                + decompressed.readableBytes()
        );
    }


