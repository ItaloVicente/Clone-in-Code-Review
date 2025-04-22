    enableWakeup = Boolean.parseBoolean(
        CouchbaseProperties.getProperty("enable_wakeup", "true", false));

    if (!enableWakeup) {
      getLogger().info("Manually disabled wakeup NOOP broadcasting through system property.");
    }

