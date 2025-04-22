    private void handleSnappyCompression(final ChannelHandlerContext ctx, final BinaryMemcacheRequest r) {
        if (!(r instanceof FullBinaryMemcacheRequest)) {
            return;
        }

        FullBinaryMemcacheRequest request = (FullBinaryMemcacheRequest) r;

        int uncompressedLength = request.content().readableBytes();
        ByteBuf compressedContent = ctx.alloc().buffer(uncompressedLength);
        ByteBuf uncompressedContent = request.content().slice();
        try {

            while (uncompressedContent.readableBytes() > 0) {
                int toRead = uncompressedContent.readableBytes();
                if (toRead > Short.MAX_VALUE) {
                    toRead = Short.MAX_VALUE;
                }
                snappy.encode(uncompressedContent, compressedContent, toRead);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Could not snappy-compress value.", ex);
        } finally {
            snappy.reset();
        }

        if (compressedContent.readableBytes() >= uncompressedLength) {
            compressedContent.release();
        } else {
            request.setDataType(DATATYPE_SNAPPY);
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
        try {
            snappy.decode(response.content(), decompressed);
        } catch (Exception ex) {
            throw new RuntimeException("Could not decode snappy-compressed value.", ex);
        } finally {
            snappy.reset();
        }

        response.content().release();
        response.setContent(decompressed);
        response.setTotalBodyLength(
            response.getExtrasLength()
                + response.getKeyLength()
                + decompressed.readableBytes()
        );
    }


