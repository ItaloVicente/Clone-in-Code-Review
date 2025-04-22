  private transient Logger logger = null;


  public SpyThread() {
    super();
  }

  public SpyThread(String name) {
    super(name);
  }

  protected Logger getLogger() {
    if (logger == null) {
      logger = LoggerFactory.getLogger(getClass());
    }
    return (logger);
  }
