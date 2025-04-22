        ByteBuf extras = ctx.alloc().buffer(8);
        extras.writeInt(request.flags());
        extras.writeInt(request.expiration());

        ByteBuf content = request.content();
        boolean compress = datatypes.compression() && env.compressionEnabled()
            && content.readableBytes() >= env.compressionLowerLimit();
        if (compress) {
            ByteBuf compressed = ctx.alloc().buffer();
            snappy.encode(content, compressed, content.readableBytes());
            content.release();
            content = compressed;
        }
