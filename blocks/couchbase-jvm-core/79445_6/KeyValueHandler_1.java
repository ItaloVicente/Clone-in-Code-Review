        byte extrasLength = 0;

        ByteBuf extras = Unpooled.EMPTY_BUFFER;

        if (msg.docFlags() != 0) {
            extrasLength = 1;
            extras = ctx.alloc().buffer(extrasLength, extrasLength);
            extras.writeByte(msg.docFlags());
        }

        FullBinaryMemcacheRequest request = new DefaultFullBinaryMemcacheRequest(key, extras, msg.content());
