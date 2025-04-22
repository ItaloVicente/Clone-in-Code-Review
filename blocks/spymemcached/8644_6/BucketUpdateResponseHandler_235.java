    return latch;
  }

  private void finerLog(String message) {
    LOGGER.log(Level.FINER, message);
  }

  @Override
  public void handleUpstream(ChannelHandlerContext context, ChannelEvent event)
    throws Exception {
    if (event instanceof ChannelStateEvent) {
      LOGGER.log(Level.FINEST, "Channel state changed: " + event + "\n\n");
