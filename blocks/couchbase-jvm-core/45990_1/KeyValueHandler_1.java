    private static BinaryMemcacheRequest handleKeepAliveRequest(KeepAliveRequest msg) {
        BinaryMemcacheRequest request = new DefaultBinaryMemcacheRequest();
        request
                .setOpcode(OP_NOOP)
                .setKeyLength((short) 0)
                .setExtras(Unpooled.EMPTY_BUFFER)
                .setExtrasLength((byte) 0)
                .setTotalBodyLength(0);
        return request;
    }

