  void setConfigurationProvider(ConfigurationProvider configProvider) {
    this.configProviderLastUpdateTimestamp = System.currentTimeMillis();
    this.configurationProvider = configProvider;
  }

  void setMinReconnectInterval(long reconnIntervalMsecs) {
    this.minReconnectInterval = reconnIntervalMsecs;
  }


