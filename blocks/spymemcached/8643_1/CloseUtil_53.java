  private static Logger logger = LoggerFactory.getLogger(CloseUtil.class);

  private CloseUtil() {
  }

  public static void close(Closeable closeable) {
    if (closeable != null) {
      try {
        closeable.close();
      } catch (Exception e) {
        logger.info("Unable to close %s", closeable, e);
      }
