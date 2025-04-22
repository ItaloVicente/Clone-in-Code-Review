        if (request == null) {
            request = encodeOtherRequest(ctx, msg);
        }

        if (msg.partition() >= 0) {
            request.setReserved(msg.partition());
        }

        request.setOpaque(msg.opaque());

        if (!(msg instanceof ObserveRequest)
            && !(msg instanceof ObserveSeqnoRequest)
            && (request instanceof FullBinaryMemcacheRequest)) {
            ((FullBinaryMemcacheRequest) request).content().retain();
        }

        return request;
    }

    private BinaryMemcacheRequest encodeCommonRequest(final ChannelHandlerContext ctx, final BinaryRequest msg) {
