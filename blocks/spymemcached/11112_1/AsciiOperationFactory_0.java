  public Operation observe(String key, long cas, long expiration,
      String observeSet, OperationCallback cb) {
    throw new UnsupportedOperationException("Observe is not supported for "
        + "ASCII protocol");
  }

