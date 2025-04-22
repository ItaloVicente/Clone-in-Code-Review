    future.addListener(new ChannelFutureListener() {
      @Override
      public void operationComplete(ChannelFuture cf) throws Exception {
        channel = cf.getChannel();
      }
    });

