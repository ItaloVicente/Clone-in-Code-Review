    private HttpRequest encodeRestApiRequest(ChannelHandlerContext ctx, RestApiRequest msg) {
        HttpMethod httpMethod = msg.method();
        ByteBuf content = Unpooled.copiedBuffer(msg.body(), CharsetUtil.UTF_8);
        String path = msg.pathWithParameters();

        FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, httpMethod, path, content);
        request.headers().set(HttpHeaders.Names.USER_AGENT, env().userAgent());
        request.headers().set(HttpHeaders.Names.HOST, remoteHttpHost(ctx));

        for (Map.Entry<String, Object> header : msg.headers().entrySet()) {
            request.headers().set(header.getKey(), header.getValue());
        }

        request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, content.readableBytes());

        addHttpBasicAuth(ctx, request, msg.bucket(), msg.password());
        return request;
    }

