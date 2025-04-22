    private static BinaryMemcacheRequest handleNoopRequest(final ChannelHandlerContext ctx, final NoopRequest msg) {
        BinaryMemcacheRequest request = new DefaultBinaryMemcacheRequest();
        request.setOpcode(OP_NOOP);
        return request;
    }

