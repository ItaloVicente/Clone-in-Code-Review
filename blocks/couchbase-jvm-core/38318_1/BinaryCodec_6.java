        ByteBuf content = request.content();
        boolean compress = datatypes.compression() && env.compressionEnabled()
            && content.readableBytes() >= env.compressionLowerLimit();
        if (compress) {
            ByteBuf compressed = ctx.alloc().buffer();
            snappy.encode(content, compressed, content.readableBytes());
            content.release();
            content = compressed;
        }

        FullBinaryMemcacheRequest msg = new DefaultFullBinaryMemcacheRequest(request.key(), extras, content);
