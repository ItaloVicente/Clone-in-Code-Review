    if (connFactory.isDefaultExecutorService()) {
      try {
        executorService.shutdown();
      } catch (Exception ex) {
        getLogger().warn("Failed shutting down the ExecutorService: ", ex);
      }
    }
