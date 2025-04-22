    private void purgeUnsubscribedMessages(ChannelHandlerContext ctx) {
        REQUEST outstanding = sentRequestQueue.peek();
        while (outstanding != null && !outstanding.isActive()) {
            sentRequestQueue.poll();
            LOGGER.debug("{}Purging {} from sent request queue since not active anymore.", logIdent(ctx, endpoint), outstanding);
            outstanding = sentRequestQueue.peek();
        }
    }

