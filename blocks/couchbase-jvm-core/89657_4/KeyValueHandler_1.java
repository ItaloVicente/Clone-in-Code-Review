        ByteBuf decompressed;
        if (response.content().readableBytes() > 0) {
            byte[] compressed = Unpooled.copiedBuffer(response.content()).array();
            try {
                decompressed = Unpooled.wrappedBuffer(Snappy.uncompress(compressed, 0, compressed.length));
            } catch (Exception ex) {
                throw new RuntimeException("Could not decode snappy-compressed value.", ex);
            }
        } else {
            decompressed = Unpooled.buffer(0);
