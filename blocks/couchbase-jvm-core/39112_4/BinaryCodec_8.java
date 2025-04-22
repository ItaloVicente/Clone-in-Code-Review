    private FullBinaryMemcacheRequest createFullBinaryMemcacheRequest(final byte opCode, final AbstractCoreDocumentBinaryRequest request, final ChannelHandlerContext ctx) {
        final CoreDocument document = request.document();
        final short keyLength = (short) document.id().length();

        final ByteBuf extras = ctx.alloc().buffer(8);
        extras.writeInt(document.flags());
        extras.writeInt(document.expiration());

        ByteBuf content = document.content();
