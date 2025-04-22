  void setConfigurationProvider(ConfigurationProvider configProvider) {
    this.configProviderLastUpdateTimestamp = System.currentTimeMillis();
    this.configurationProvider = configProvider;
  }

  void setMinReconnectInterval(long minReconnectInterval) {
    this.minReconnectInterval = minReconnectInterval;
  }


