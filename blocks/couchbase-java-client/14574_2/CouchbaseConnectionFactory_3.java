
  protected void requestConfigReconnect(String bucketName, Reconfigurable rec) {
    configurationProvider.markForResubscribe(bucketName, rec);
    needsReconnect = true;
  }

  void checkConfigUpdate() {
    if (needsReconnect || pastReconnThreshold()) {
      LOGGER.log(Level.INFO,
                 "Attempting to resubscribe for cluster config updates.");
      configurationProvider =
        new ConfigurationProviderHTTP(storedBaseList, bucket, pass);
      configurationProvider.finishResubscribe();
    } else {
      LOGGER.log(Level.WARNING, "No reconnect required, though check requested."
              + " Current config check is {0} out of a threshold of {1}.",
              new Object[]{configThresholdCount, maxConfigCheck});
    }
  }

  private boolean pastReconnThreshold() {
    long currentTime = System.nanoTime();
    if (currentTime - thresholdLastCheck > 100000000) { //if longer than 10 sec
      configThresholdCount = 0;
    }
    configThresholdCount++;
    thresholdLastCheck = currentTime;

    if (configThresholdCount >= maxConfigCheck) {
      return true;
    }
    return false;
  }

