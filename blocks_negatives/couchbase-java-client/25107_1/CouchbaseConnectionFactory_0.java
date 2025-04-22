      Bucket config = configurationProvider.getBucketConfiguration(bucket);
      if(config == null) {
        throw new ConfigurationException("Could not fetch valid configuration "
          + "from provided nodes. Stopping.");
      } else if (config.isNotUpdating()) {
        LOGGER.warning("Noticed bucket configuration to be disconnected, "
            + "will attempt to reconnect");
        setConfigurationProvider(new ConfigurationProviderHTTP(storedBaseList,
          bucket, pass));
      }
      return configurationProvider.getBucketConfiguration(bucket).getConfig();
