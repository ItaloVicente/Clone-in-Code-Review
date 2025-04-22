      configurationProvider.markForResubscribe(
        oldConfigProvider.getBucket(), oldConfigProvider.getReconfigurable());
      configurationProvider.finishResubscribe();
      Bucket bucket = configurationProvider.getBucketConfiguration(
                                     configurationProvider.getBucket());
      configurationProvider.getReconfigurable().reconfigure(bucket);

