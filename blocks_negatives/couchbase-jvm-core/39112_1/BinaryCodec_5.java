
    /**
     * Creates the actual protocol level request for an incoming bucket config request.
     *
     * @return the built protocol request.
     */
    private BinaryMemcacheRequest handleGetBucketConfigRequest() {
        BinaryMemcacheRequest msg = new DefaultBinaryMemcacheRequest();
        msg.setOpcode((byte) 0xb5);
        return msg;
    }

