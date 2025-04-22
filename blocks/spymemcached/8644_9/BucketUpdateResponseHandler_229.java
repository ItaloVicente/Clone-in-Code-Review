  private volatile boolean readingChunks;
  private String lastResponse;
  private ChannelFuture receivedFuture;
  private CountDownLatch latch;
  private StringBuilder partialResponse;
  private BucketMonitor monitor;
  private static final Logger LOGGER =
      Logger.getLogger(BucketUpdateResponseHandler.class.getName());

  @Override
  public void messageReceived(final ChannelHandlerContext context,
      final MessageEvent event) {
    ChannelFuture channelFuture = event.getFuture();
    setReceivedFuture(channelFuture);
    if (this.partialResponse == null) {
      this.partialResponse = new StringBuilder();
