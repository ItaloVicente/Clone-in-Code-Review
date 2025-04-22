            return handleUnlockRequest((UnlockRequest) msg);
        }
        return null;
    }

    private BinaryMemcacheRequest encodeOtherRequest(final ChannelHandlerContext ctx, final BinaryRequest msg) {
        if (msg instanceof ObserveRequest) {
            return handleObserveRequest(ctx, (ObserveRequest) msg);
