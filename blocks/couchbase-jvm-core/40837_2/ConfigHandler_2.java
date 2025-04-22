            content = Unpooled.EMPTY_BUFFER;
        }

        FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, httpMethod, msg.path(), content);
        if (msg instanceof InsertBucketRequest || msg instanceof UpdateBucketRequest) {
            request.headers().set(HttpHeaders.Names.ACCEPT, "*/*");
            request.headers().set(HttpHeaders.Names.CONTENT_TYPE, "application/x-www-form-urlencoded");
