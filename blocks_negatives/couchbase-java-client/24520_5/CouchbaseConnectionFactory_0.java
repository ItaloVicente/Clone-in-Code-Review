          ConfigurationProvider newConfigProvider =
            new ConfigurationProviderHTTP(storedBaseList, bucket, pass);
          setConfigurationProvider(newConfigProvider);

          newConfigProvider.subscribe(bucket,
            oldConfigProvider.getReconfigurable());

