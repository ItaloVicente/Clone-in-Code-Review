
    manageTaintedConfig(config.getConfig());
  }

  private void manageTaintedConfig(final Config config) {
    if (!isBinary) {
      return;
    }

    if (config.isTainted() && pollingBinary.compareAndSet(false, true)) {
      getLogger().debug("Found tainted configuration, starting carrier "
        + "poller.");
      Thread thread = new Thread(new BinaryConfigPoller());
      thread.setName("couchbase - carrier config poller");
      thread.start();
    }
