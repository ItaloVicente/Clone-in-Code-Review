
  protected void requestConfigReconnect(String bucketName, Reconfigurable rec) {
    configurationProvider.markForResubscribe(bucketName, rec);
    needsReconnect = true;
  }

  void checkConfigUpdate() {
    if (needsReconnect) {
      LOGGER.log(Level.INFO,
                 "Attempting to resubscribe for cluster config updates.");
      configurationProvider =
        new ConfigurationProviderHTTP(storedBaseList, bucket, pass);
      configurationProvider.finishResubscribe();
    } else {
      LOGGER.log(Level.FINER, "No reconnect required, though check requested.");
    }
  }

