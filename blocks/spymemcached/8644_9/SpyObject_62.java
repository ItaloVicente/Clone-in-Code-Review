  private transient Logger logger = null;

  public SpyObject() {
    super();
  }

  protected Logger getLogger() {
    if (logger == null) {
      logger = LoggerFactory.getLogger(getClass());
    }
    return (logger);
  }
