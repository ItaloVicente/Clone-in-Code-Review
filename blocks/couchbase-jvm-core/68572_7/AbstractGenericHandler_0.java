    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (sentRequestQueue.size() < sentQueueLimit) {
            super.write(ctx, msg, promise);
        } else {
            LOGGER.debug("Rescheduling {} because sentRequestQueueLimit reached.", msg);
            RetryHelper.retryOrCancel(env(), (CouchbaseRequest) msg, responseBuffer);
        }
    }

