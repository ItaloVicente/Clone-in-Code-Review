
      LOGGER.log(Level.INFO,
                 "Attempting to resubscribe for cluster config updates.");
      ConfigurationProvider oldConfigProvider = this.configurationProvider;
      setConfigurationProvider(new ConfigurationProviderHTTP(storedBaseList,
        bucket, pass));
      configurationProvider.finishResubscribe();

      if (null != oldConfigProvider) {
        oldConfigProvider.shutdown();
