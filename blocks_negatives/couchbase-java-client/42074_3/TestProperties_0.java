  /**
   * Initialize static the properties.
   */
  static {
    seedNode = System.getProperty("seedNode", "127.0.0.1");
    bucket = System.getProperty("bucket", "default");
    password = System.getProperty("password", "");
    adminName = System.getProperty("adminName", "Administrator");
    adminPassword = System.getProperty("adminPassword", "password");
  }
