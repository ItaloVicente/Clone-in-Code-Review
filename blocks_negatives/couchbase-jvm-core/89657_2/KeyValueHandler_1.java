        ByteBuf decompressed = ctx.alloc().buffer(response.content().readableBytes());
        try {
            snappy.decode(response.content(), decompressed);
        } catch (Exception ex) {
            throw new RuntimeException("Could not decode snappy-compressed value.", ex);
        } finally {
            snappy.reset();
