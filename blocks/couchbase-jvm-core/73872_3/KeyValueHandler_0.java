
        ByteBuf content;
        if (copyContent) {
            content = msg.content().copy();
        } else {
            content = msg.content();
        }

        FullBinaryMemcacheRequest request = new DefaultFullBinaryMemcacheRequest(key, extras, content);
