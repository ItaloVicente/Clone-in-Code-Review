    /**
     * Add basic authentication headers to a {@link HttpRequest}.
     *
     * The given information is Base64 encoded and the authorization header is set appropriately. Since this needs
     * to be done for every request, it is refactored out.
     *
     * @param ctx the handler context.
     * @param request the request where the header should be added.
     * @param user the username for auth.
     * @param password the password for auth.
     */
    private static void addAuth(final ChannelHandlerContext ctx, final HttpRequest request, final String user,
        final String password) {
        final String pw = password == null ? "" : password;

        ByteBuf raw = ctx.alloc().buffer(user.length() + pw.length() + 1);
        raw.writeBytes((user + ":" + pw).getBytes(CHARSET));
        ByteBuf encoded = Base64.encode(raw, false);
        request.headers().add(HttpHeaders.Names.AUTHORIZATION, "Basic " + encoded.toString(CHARSET));
        encoded.release();
        raw.release();
    }

