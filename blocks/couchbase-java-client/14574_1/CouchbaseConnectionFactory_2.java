      if (configurationProvider.getBucketConfiguration(bucket)
           .isNotUpdating()) {
        LOGGER.warning("Noticed bucket configuration to be disconnected, "
            + "will attempt to reconnect");
        configurationProvider = new ConfigurationProviderHTTP(storedBaseList,
            bucket, pass);
      }
