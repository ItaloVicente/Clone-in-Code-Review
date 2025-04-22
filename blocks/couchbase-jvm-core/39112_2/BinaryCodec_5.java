    private BinaryMemcacheRequest handleGetBucketConfigRequest() {
        final BinaryMemcacheRequest msg = new DefaultBinaryMemcacheRequest();
        msg.setOpcode((byte) 0xb5);

        return msg;
    }
