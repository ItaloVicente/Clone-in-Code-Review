    private volatile boolean readingChunks;
    private String lastResponse;
    private ChannelFuture receivedFuture;
    private CountDownLatch latch;
    private StringBuilder partialResponse;
    private static final Logger LOGGER = Logger.getLogger(BucketUpdateResponseHandler.class.getName());

    @Override
    public void messageReceived(final ChannelHandlerContext context, final MessageEvent event) {
        ChannelFuture channelFuture = event.getFuture();
        setReceivedFuture(channelFuture);
        if (this.partialResponse == null) {
            this.partialResponse = new StringBuilder();
        }
        if (readingChunks) {
            HttpChunk chunk = (HttpChunk) event.getMessage();
            if (chunk.isLast()) {
                readingChunks = false;
            } else {
                String curChunk = chunk.getContent().toString("UTF-8");
                /*
                 * Server sends four new lines in a chunk as a sentinal between
                 * responses.
                 */
                if (curChunk.matches("\n\n\n\n")) {
                    setLastResponse(partialResponse.toString());
                    partialResponse = null;
                    getLatch().countDown();
                    if (monitor != null) {
                        monitor.invalidate();
                    }
                } else {
                    finerLog(curChunk);
                    finerLog("Chunk length is: " + curChunk.length());
                    partialResponse.append(curChunk);
                    channelFuture.setSuccess();
                }

            }
        } else {
            HttpResponse response = (HttpResponse) event.getMessage();
            logResponse(response);
        }
