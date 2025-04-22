        ByteBuf content;
        if (msg instanceof InsertBucketRequest) {
            content = Unpooled.copiedBuffer(((InsertBucketRequest) msg).payload(), CharsetUtil.UTF_8);
        } else {
            content = Unpooled.EMPTY_BUFFER;
        }
        FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, httpMethod, msg.path(), content);
