  public static void close(Closeable closeable) {
    if (closeable != null) {
      try {
        closeable.close();
      } catch (Exception e) {
        logger.info("Unable to close %s", closeable, e);
      }
    }
  }
