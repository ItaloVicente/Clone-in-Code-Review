
    ChannelFuture channelFuture = createChannel();

    final CountDownLatch channelLatch = new CountDownLatch(1);
    channelFuture.addListener(new ChannelFutureListener() {
      @Override
      public void operationComplete(ChannelFuture cf) throws Exception {
        if(cf.isSuccess()) {
          channel = cf.getChannel();
          channelLatch.countDown();
        } else {
          bootstrap.releaseExternalResources();
          throw new ConnectionException("Could not connect to any cluster pool "
            + "member.");
        }
      }
    });

    try {
      channelLatch.await();
    } catch(InterruptedException ex) {
      throw new ConnectionException("Interrupted while waiting for streaming "
        + "connection to arrive.");
    }

