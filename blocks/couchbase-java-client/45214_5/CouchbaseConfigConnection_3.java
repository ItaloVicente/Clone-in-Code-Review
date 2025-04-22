
    noopsThreshold = Short.decode(CouchbaseProperties.getProperty(
      "configPollThreshold",
      DEFAULT_MISSED_NOOPS_THRESHOLD
    ));

    LOGGER.debug("Using config noop threshold of " + noopsThreshold);
