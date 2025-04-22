  protected Logger getLogger() {
    if (logger == null) {
      logger = LoggerFactory.getLogger(getClass());
    }
    return (logger);
  }
