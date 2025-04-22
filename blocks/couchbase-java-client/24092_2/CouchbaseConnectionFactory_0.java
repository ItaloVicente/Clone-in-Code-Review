      do {
        try {
          ConfigurationProvider oldConfigProvider = configurationProvider;
          setConfigurationProvider(new ConfigurationProviderHTTP(storedBaseList,
            bucket, pass));

          if (null != oldConfigProvider) {
            oldConfigProvider.shutdown();
          }

          configurationProvider.markForResubscribe(
            oldConfigProvider.getBucket(), oldConfigProvider.getReconfigurable());
          configurationProvider.finishResubscribe();
          Bucket oldBucket = configurationProvider.getBucketConfiguration(
                                         configurationProvider.getBucket());
          configurationProvider.getReconfigurable().reconfigure(oldBucket);

          if (!doingResubscribe.compareAndSet(true, false)) {
            LOGGER.log(Level.WARNING, "Could not reset from doing a resubscribe.");
          }
        } catch (Exception ex) {
          LOGGER.log(Level.WARNING,
            "Could not resubscribe because of: " + ex.getMessage(), ex);
        }
      } while(doingResubscribe.get());
