
    @Override
    public void handleUpstream(ChannelHandlerContext context, ChannelEvent event) throws Exception {

        if (event instanceof ChannelStateEvent) {
            LOGGER.log(Level.FINEST, "Channel state changed: " + event + "\n\n");
        }
        super.handleUpstream(context, event);
