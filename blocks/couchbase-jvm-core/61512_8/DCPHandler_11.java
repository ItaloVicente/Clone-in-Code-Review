
        return request;
    }

    private BinaryMemcacheRequest handleStreamCloseRequest(ChannelHandlerContext ctx, StreamCloseRequest msg) {
        BinaryMemcacheRequest request = new DefaultBinaryMemcacheRequest();
        request.setOpcode(OP_STREAM_CLOSE);
