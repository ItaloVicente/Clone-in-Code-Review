      channel.writeAndFlush(request).await(30, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      throw new RuntimeException("Interrupted while waiting to write the " +
        "streaming config request.");
    }

    try {
      String response = handler.getCurrentConfig();
