    private class KeepAliveResponseAction implements Action1<CouchbaseResponse> {
        private final ChannelHandlerContext ctx;
        public KeepAliveResponseAction(ChannelHandlerContext ctx) { this.ctx = ctx; }

        @Override
        public void call(CouchbaseResponse couchbaseResponse) {
            onKeepAliveResponse(this.ctx, couchbaseResponse);
        }
    }
