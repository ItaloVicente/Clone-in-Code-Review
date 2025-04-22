        long cas = msg.getCAS();
        if(current instanceof GetBucketConfigRequest) {
            InetSocketAddress addr = (InetSocketAddress) ctx.channel().remoteAddress();
            in.add(
                new GetBucketConfigResponse(
                    convertStatus(msg.getStatus()),
                    bucket,
                    msg.content().copy(),
                    InetAddress.getByName(addr.getHostName())
                )
            );
        } else if (current instanceof GetRequest) {
            ByteBuf content = msg.content().copy();
            if (msg.getDataType() == 2 || msg.getDataType() == 3) {
                ByteBuf compressed = ctx.alloc().buffer();
                snappy.decode(content, compressed);
                content.release();
                content = compressed;
            }
            int flags = 0;
            if (msg.getExtrasLength() > 0)
            {
                final ByteBuf extrasReleased = msg.getExtras();
                final ByteBuf extras = ctx.alloc().buffer(msg.getExtrasLength());
                extras.writeBytes(extrasReleased, extrasReleased.readerIndex(), extrasReleased.readableBytes());
                flags = extras.getInt(0);
                extras.release();
