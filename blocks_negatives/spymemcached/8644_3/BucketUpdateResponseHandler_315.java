    /**
     * @return the lastResponse
     */
    protected String getLastResponse() {
        ChannelFuture channelFuture = getReceivedFuture();
        if (channelFuture.awaitUninterruptibly(30, TimeUnit.SECONDS)) {
            return lastResponse;
            throw new ConnectionException("Cannot contact any server in the pool");
        }
