  @Override
  protected void handleRetryInformation(byte[] retryMessage) {
    String message = new String(retryMessage).trim();
    if (message.startsWith("{")) {
      cf.getConfigurationProvider().updateBucket(
        replaceConfigWildcards(message)
      );
    }
  }

  private String replaceConfigWildcards(String original) {
    if (original.contains("$HOST")) {
      ArrayList<MemcachedNode> nodes =
        new ArrayList<MemcachedNode>(getLocator().getAll());
      if (nodes.size() > 0) {
        InetSocketAddress addr = (InetSocketAddress) nodes.get(0)
          .getSocketAddress();
        return original.replaceAll("\\$HOST", addr.getHostName());
      } else {
        throw new IllegalStateException("Locator has no nodes attached, "
          + "this is not allowed.");
      }
    }

    return original;
  }

