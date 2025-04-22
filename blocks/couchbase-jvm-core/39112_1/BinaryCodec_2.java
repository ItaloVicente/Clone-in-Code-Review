    private BinaryMemcacheRequest handleGetBucketConfigRequest() {
        final BinaryMemcacheRequest msg = new DefaultBinaryMemcacheRequest();
        msg.setOpcode((byte) 0xb5);

        return msg;
    }

    private FullBinaryMemcacheRequest createFullBinaryMemcacheRequest(final byte opCode, final AbstractCoreDocumentBinaryRequest request, final ChannelHandlerContext ctx) {
        final short keyLength = (short) request.key().length();
        final CoreDocument document = request.document();
