  @Override
  protected void handleRetryInformation(final byte[] retryMessage) {
    String message = new String(retryMessage).trim();
    if (message.startsWith("{")) {
      cf.getConfigurationProvider().updateBucket(message);
    }
  }

