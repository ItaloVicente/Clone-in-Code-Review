  @Override
  protected void queueReconnect(final MemcachedNode node) {
    cf.getConfigurationProvider().reloadConfig();
    if (isShutDown() || !locator.getAll().contains(node)) {
      getLogger().debug("Preventing reconnect for node " + node + " because it"
        + "is either not part of the cluster anymore or the connection is "
        + "shutting down.");
      return;
    }

    super.queueReconnect(node);
  }

