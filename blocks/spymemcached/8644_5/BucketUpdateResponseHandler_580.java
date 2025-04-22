  }

  protected String getLastResponse() {
    ChannelFuture channelFuture = getReceivedFuture();
    if (channelFuture.awaitUninterruptibly(30, TimeUnit.SECONDS)) {
      return lastResponse;
    } else { // TODO: make this work with multiple servers
      throw new ConnectionException("Cannot contact any server in the pool");
