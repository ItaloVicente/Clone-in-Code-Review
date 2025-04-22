  public static final Platform platform;
  public static Method canExecute;
  public static Method setExecute;
  public static String cygpath;

  static {
    platform = Platform.detect();
  }
