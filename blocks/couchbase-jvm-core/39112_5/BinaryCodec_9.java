        int flags = 0;
        int expiration = 0;
        if (msg.getExtrasLength() > 0) {
            final ByteBuf extrasReleased = msg.getExtras();
            final ByteBuf extras = ctx.alloc().buffer(msg.getExtrasLength());
            extras.writeBytes(extrasReleased, extrasReleased.readerIndex(), extrasReleased.readableBytes());
            flags = extras.getInt(0);
            if (msg.getExtrasLength() > 4) {
                expiration = extras.getInt(1);
