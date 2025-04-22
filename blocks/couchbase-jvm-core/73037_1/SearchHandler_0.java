        FullHttpRequest request;

        if (msg instanceof KeepAliveRequest) {
            request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, httpMethod, msg.path(), content);
            request.headers().set(HttpHeaders.Names.USER_AGENT, env().userAgent());
            request.headers().set(HttpHeaders.Names.HOST, remoteHttpHost(ctx));
        } else {
            request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, httpMethod, msg.path(), content);
            request.headers().set(HttpHeaders.Names.USER_AGENT, env().userAgent());
            if (msg instanceof UpsertSearchIndexRequest || msg instanceof SearchQueryRequest) {
                request.headers().set(HttpHeaders.Names.ACCEPT, "*/*");
                request.headers().set(HttpHeaders.Names.CONTENT_TYPE, "application/json");
            }
            request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, content.readableBytes());
            request.headers().set(HttpHeaders.Names.HOST, remoteHttpHost(ctx));
            addHttpBasicAuth(ctx, request, msg.bucket(), msg.password());
