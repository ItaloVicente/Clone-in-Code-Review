    super.handleUpstream(context, event);
  }

  protected void setBucketMonitor(BucketMonitor newMonitor) {
    this.monitor = newMonitor;
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
    throws Exception {
    LOGGER.log(Level.INFO, "Exception occurred: ");
    if (monitor != null) {
      monitor.invalidate();
