        if (msg instanceof KeepAliveRequest) {
            FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.HEAD, "/",
                    Unpooled.EMPTY_BUFFER);
            request.headers().set(HttpHeaders.Names.USER_AGENT, env().userAgent());
            request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, 0);
            return request;
        }

