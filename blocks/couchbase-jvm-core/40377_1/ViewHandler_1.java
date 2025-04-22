        if (content == null) {
            content =  Unpooled.buffer(0);
        }
        FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, method, path.toString(), content);
        request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, content.readableBytes());
        request.headers().set(HttpHeaders.Names.CONTENT_TYPE, "application/json");
