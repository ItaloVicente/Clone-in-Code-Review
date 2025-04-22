  private static final String MODE_PRODUCTION = "production";
  private static final String MODE_DEVELOPMENT = "development";
  private static final String DEV_PREFIX = "dev_";
  private static final String PROD_PREFIX = "";
  private static String modePrefix = PROD_PREFIX;
  private static String modeMessage = "";

  private static String obsPollIntervalProp;
  private static String obsPollMaxProp;
  private static String shouldOptimizeProp;
  private static String opTimeoutProp;
  private static String timeoutExceptionThresholdProp;
  private static String maxReconnectDelayProp;
  private static String readBufSizeProp;
  private static String isDaemonProp;
  private static String opQueueMaxBlockTimeProp;
  private static String reconnThresholdTimeProp;

