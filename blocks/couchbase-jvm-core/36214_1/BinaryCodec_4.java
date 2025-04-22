        return msg;
    }

    private BinaryMemcacheRequest handleReplaceRequest(final ReplaceRequest request, final ChannelHandlerContext ctx) {
        ByteBuf extras = ctx.alloc().buffer(8);
        extras.writeInt(request.flags());
        extras.writeInt(request.expiration());
