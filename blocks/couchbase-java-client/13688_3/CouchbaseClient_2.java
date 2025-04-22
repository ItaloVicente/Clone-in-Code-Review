  static {
    Properties properties = new Properties(System.getProperties());
    String viewmode = properties.getProperty("viewmode", null);

    if (viewmode == null) {
      try {
        URL url =  ClassLoader.getSystemResource("cbclient.properties");
        if (url != null) {
          properties.load(new FileInputStream(new File(url.getFile())));
        }
        viewmode = properties.getProperty("viewmode");
      } catch (IOException e) {
      }
    }

    if (viewmode == null) {
      MODE_ERROR = "viewmode property isn't defined. Setting viewmode to"
        + " production mode";
      MODE_PREFIX = PROD_PREFIX;
    } else if (viewmode.equals(MODE_PRODUCTION)) {
      MODE_ERROR = "viewmode set to production mode";
      MODE_PREFIX = PROD_PREFIX;
    } else if (viewmode.equals(MODE_DEVELOPMENT)) {
      MODE_ERROR = "viewmode set to development mode";
      MODE_PREFIX = DEV_PREFIX;
    } else {
      MODE_ERROR = "unknown value \"" + viewmode + "\" for property viewmode"
          + " Setting to production mode";
      MODE_PREFIX = PROD_PREFIX;
    }
  }

