  private class Resubscriber implements Runnable {

    public void run() {
      String threadNameBase = "couchbase cluster resubscriber - ";
      Thread.currentThread().setName(threadNameBase + "running");
      LOGGER.log(Level.CONFIG, "Starting resubscription for bucket {0}",
        bucket);
      LOGGER.log(Level.CONFIG, "Resubscribing for {0} using base list {1}",
        new Object[]{bucket, storedBaseList});
      ConfigurationProvider oldConfigProvider = configurationProvider;
      setConfigurationProvider(new ConfigurationProviderHTTP(storedBaseList,
        bucket, pass));
      configurationProvider.finishResubscribe();
      if (null != oldConfigProvider) {
        oldConfigProvider.shutdown();
      }

      if (!doingResubscribe.compareAndSet(true, false)) {
        assert false : "Could not reset from doing a resubscribe.";
      }
        Thread.currentThread().setName(threadNameBase + "complete");
    }
  }

