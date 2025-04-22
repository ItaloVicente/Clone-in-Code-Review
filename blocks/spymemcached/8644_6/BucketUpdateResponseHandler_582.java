    return receivedFuture;
  }

  private void setReceivedFuture(ChannelFuture newReceivedFuture) {
    this.receivedFuture = newReceivedFuture;
  }

  private CountDownLatch getLatch() {
    if (this.latch == null) {
      latch = new CountDownLatch(1);
