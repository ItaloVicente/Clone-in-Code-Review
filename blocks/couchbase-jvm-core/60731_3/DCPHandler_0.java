    private BinaryMemcacheRequest handleGetLastCheckpointRequest(ChannelHandlerContext ctx, GetLastCheckpointRequest msg) {
        BinaryMemcacheRequest request = new DefaultBinaryMemcacheRequest();
        request.setOpcode(OP_GET_LAST_CHECKPOINT);
        request.setReserved(msg.partition());

        return request;
    }

