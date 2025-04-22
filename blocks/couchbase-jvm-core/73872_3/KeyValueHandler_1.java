
        ByteBuf content;
        if (copyContent) {
            content = msg.content().copy();
        } else {
            content = msg.content();
        }

        BinaryMemcacheRequest request = new DefaultFullBinaryMemcacheRequest(key, Unpooled.EMPTY_BUFFER, content);
