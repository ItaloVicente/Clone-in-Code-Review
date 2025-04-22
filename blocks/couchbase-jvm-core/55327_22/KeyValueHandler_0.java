    private static BinaryMemcacheRequest handleStatRequest(StatRequest msg) {
        String key = msg.key();
        BinaryMemcacheRequest request = new DefaultBinaryMemcacheRequest(key);
        short keyLength = (short) msg.keyBytes().length;
        request
                .setOpcode(OP_STAT)
                .setKeyLength(keyLength)
                .setTotalBodyLength(keyLength);
        return request;
    }

