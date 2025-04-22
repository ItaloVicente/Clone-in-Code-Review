
    public static void addHttpBasicAuth(final ChannelHandlerContext ctx, final HttpRequest request, final String user,
        final String password) {
        final String pw = password == null ? "" : password;

        ByteBuf raw = ctx.alloc().buffer(user.length() + pw.length() + 1);
        raw.writeBytes((user + ":" + pw).getBytes(CHARSET));
        ByteBuf encoded = Base64.encode(raw, false);
        request.headers().add(HttpHeaders.Names.AUTHORIZATION, "Basic " + encoded.toString(CHARSET));
        encoded.release();
        raw.release();
    }

