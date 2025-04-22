    private static BinaryMemcacheRequest handleAppendRequest(final AppendRequest msg) {
        String key = msg.key();
        short keyLength = (short) key.length();
        BinaryMemcacheRequest request = new DefaultFullBinaryMemcacheRequest(key, Unpooled.EMPTY_BUFFER, msg.content());

        request.setOpcode(OP_APPEND);
        request.setKeyLength(keyLength);
        request.setCAS(msg.cas());
        request.setTotalBodyLength(keyLength + msg.content().readableBytes());
        return request;
    }

    private static BinaryMemcacheRequest handlePrependRequest(final PrependRequest msg) {
        String key = msg.key();
        short keyLength = (short) key.length();
        BinaryMemcacheRequest request = new DefaultFullBinaryMemcacheRequest(key, Unpooled.EMPTY_BUFFER, msg.content());

        request.setOpcode(OP_PREPEND);
        request.setKeyLength(keyLength);
        request.setCAS(msg.cas());
        request.setTotalBodyLength(keyLength + msg.content().readableBytes());
        return request;
    }

