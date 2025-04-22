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
