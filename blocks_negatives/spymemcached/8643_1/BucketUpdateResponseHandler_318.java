    /**
     * @return the receivedFuture
     */
    private ChannelFuture getReceivedFuture() {
        try {
            getLatch().await();
        } catch (InterruptedException ex) {
            finerLog("Getting received future has been interrupted.");
