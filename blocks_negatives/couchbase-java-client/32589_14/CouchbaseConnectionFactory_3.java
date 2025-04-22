  private class Resubscriber implements Runnable {

    public void run() {
      String threadNameBase = "Couchbase/Resubscriber (Status: ";
      Thread.currentThread().setName(threadNameBase + "running)");
      LOGGER.log(Level.CONFIG, "Resubscribing for {0} using base list {1}",
        new Object[]{bucket, storedBaseList});

      long reconnectAttempt = 0;
      long backoffTime = 1000;
      long maxWaitTime = 10000;
      do {
        try {
          long waitTime = (reconnectAttempt++)*backoffTime;
          if(reconnectAttempt >= 10) {
            waitTime = maxWaitTime;
          }
          LOGGER.log(Level.INFO, "Reconnect attempt {0}, waiting {1}ms",
            new Object[]{reconnectAttempt, waitTime});
          Thread.sleep(waitTime);

          ConfigurationProvider oldConfigProvider = getConfigurationProvider();
          Reconfigurable oldRec = oldConfigProvider.getReconfigurable();

          ConfigurationProvider newConfigProvider =
            new ConfigurationProviderHTTP(storedBaseList, bucket, pass);
          newConfigProvider.subscribe(bucket, oldRec);

          setConfigurationProvider(newConfigProvider);
          oldConfigProvider.shutdown();

          if (!doingResubscribe.compareAndSet(true, false)) {
            LOGGER.log(Level.WARNING,
              "Could not reset from doing a resubscribe.");
          }
        } catch (Exception ex) {
          LOGGER.log(Level.WARNING,
            "Resubscribe attempt failed: ", ex);
        }
      } while(doingResubscribe.get());

      Thread.currentThread().setName(threadNameBase + "complete)");
    }
  }

