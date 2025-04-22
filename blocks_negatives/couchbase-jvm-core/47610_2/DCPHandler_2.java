        if (request instanceof FullBinaryMemcacheRequest) {
            ByteBuf content = ((FullBinaryMemcacheRequest) request).content();
            if (content != null) {
                content.retain();
            }
        }
