      do {
        try {
          ConfigurationProvider oldConfigProvider = getConfigurationProvider();

          if (null != oldConfigProvider) {
            oldConfigProvider.shutdown();
          }

          ConfigurationProvider newConfigProvider =
            new ConfigurationProviderHTTP(storedBaseList, bucket, pass);
          setConfigurationProvider(newConfigProvider);

          newConfigProvider.subscribe(bucket,
            oldConfigProvider.getReconfigurable());

          if (!doingResubscribe.compareAndSet(true, false)) {
            LOGGER.log(Level.WARNING,
              "Could not reset from doing a resubscribe.");
          }
        } catch (Exception ex) {
          LOGGER.log(Level.WARNING,
            "Could not resubscribe because of: " + ex.getMessage(), ex);
        }
      } while(doingResubscribe.get());
