  private static final String MODE_PRODUCTION = "production";
  private static final String MODE_DEVELOPMENT = "development";
  private static final String DEV_PREFIX = "dev_";
  private static final String PROD_PREFIX = "";
  private static String modePrefix = PROD_PREFIX;
  private static String modeMessage = "";

  static String obsPollIntervalProp;
  static String obsPollMaxProp;
  static String shouldOptimizeProp;
  static String opTimeoutProp;

  private long obsPollInterval;
  private int obsPollMax;
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
  }

