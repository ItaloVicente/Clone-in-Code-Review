  private long reconnThresholdTimeMsecs =
    CouchbaseConnectionFactory.DEFAULT_MIN_RECONNECT_INTERVAL;
  static {
    Properties properties = new Properties();

    FileInputStream fs = null;
    try {
      URL url = ClassLoader.getSystemResource("cbclient.properties");
      if (url != null) {
        fs = new FileInputStream(new File(url.getFile()));
        properties.load(fs);
      }
    } catch (IOException e) {
    } finally {
      if (fs != null) {
        CloseUtil.close(fs);
      }
    }
    properties.putAll(System.getProperties());

    String viewmode = properties.getProperty("viewmode", MODE_PRODUCTION);
    if (viewmode == null) {
      modeMessage = "viewmode property isn't defined. Setting viewmode to"
        + " production mode";
      modePrefix = PROD_PREFIX;
    } else if (viewmode.equals(MODE_PRODUCTION)) {
      modeMessage = "viewmode set to production mode";
      modePrefix = PROD_PREFIX;
    } else if (viewmode.equals(MODE_DEVELOPMENT)) {
      modeMessage = "viewmode set to development mode";
      modePrefix = DEV_PREFIX;
    } else {
      modeMessage = "unknown value \"" + viewmode + "\" for property viewmode"
          + " Setting to production mode";
      modePrefix = PROD_PREFIX;
    }

    obsPollIntervalProp = properties.getProperty("obsPollInterval");
    obsPollMaxProp = properties.getProperty("obsPollMax");
    shouldOptimizeProp = properties.getProperty("shouldOptimize");
    opTimeoutProp = properties.getProperty("opTimeout");
    timeoutExceptionThresholdProp =
            properties.getProperty("timeoutExceptionThreshold");
    maxReconnectDelayProp = properties.getProperty("maxReconnectDelay");
    readBufSizeProp = properties.getProperty("readBufSize");
    isDaemonProp = properties.getProperty("isDaemon");
    opQueueMaxBlockTimeProp = properties.getProperty("opQueueMaxBlockTime");
    reconnThresholdTimeProp = properties.getProperty("reconnThresholdTime");
  }

  public CouchbaseConnectionFactoryBuilder() {
    super();
    if (obsPollIntervalProp != null) {
      obsPollInterval = Long.parseLong(obsPollIntervalProp);
    }
    if (obsPollMaxProp != null) {
      obsPollMax = Integer.parseInt(obsPollMaxProp);
    }
    if (opTimeoutProp != null) {
      opTimeout = Long.parseLong(opTimeoutProp);
    }
    if (shouldOptimizeProp != null) {
      shouldOptimize = Boolean.parseBoolean(shouldOptimizeProp);
    }
    if (timeoutExceptionThresholdProp != null) {
      timeoutExceptionThreshold =
              Integer.parseInt(timeoutExceptionThresholdProp);
    }
    if (maxReconnectDelayProp != null) {
      maxReconnectDelay = Integer.parseInt(maxReconnectDelayProp);
    }
    if (readBufSizeProp != null) {
      readBufSize = Integer.parseInt(readBufSizeProp);
    }
    if (isDaemonProp != null) {
      isDaemon = Boolean.parseBoolean(isDaemonProp);
    }
    if (opQueueMaxBlockTimeProp != null) {
      opQueueMaxBlockTime = Integer.parseInt(opQueueMaxBlockTimeProp);
    }
    if (reconnThresholdTimeProp != null) {
      reconnThresholdTimeMsecs = Integer.parseInt(reconnThresholdTimeProp);
    }
  }

  private Config vBucketConfig;
