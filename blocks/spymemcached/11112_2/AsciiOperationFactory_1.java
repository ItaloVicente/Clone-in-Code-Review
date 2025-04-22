  public Operation unobserve(String key, long cas, String observeSet,
      OperationCallback cb) {
    throw new UnsupportedOperationException("Unobserve is not supported for "
        + "ASCII protocol");
  }

