  private final SimpleDateFormat df;

  public DefaultLogger(String name) {
    super(name);
    df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
  }

  @Override
  public boolean isDebugEnabled() {
    return (false);
  }

  @Override
  public boolean isInfoEnabled() {
    return (true);
  }
