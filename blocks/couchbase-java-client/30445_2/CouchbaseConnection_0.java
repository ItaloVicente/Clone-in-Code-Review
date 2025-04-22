  @Override
  protected void handleRetryInformation(final byte[] retryMessage) {
    String message = new String(retryMessage);
    if (message.startsWith("{")) {
      cf.getConfigurationProvider().updateBucket(message);
    }
  }

