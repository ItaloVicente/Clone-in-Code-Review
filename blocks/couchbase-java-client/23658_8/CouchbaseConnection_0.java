
    boolean enableThrottling = Boolean.parseBoolean(
      CouchbaseProperties.getProperty("enable_throttle", false));
    if(enableThrottling) {
      this.throttleManager = new ThrottleManager<AdaptiveThrottler>(
        a, AdaptiveThrottler.class, this, opfactory);
    } else {
      this.throttleManager = null;
    }
