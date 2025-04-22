  public ObserveOperation observe(String key, long casId, int index,
      ObserveOperation.Callback cb) {
    throw new UnsupportedOperationException("Observe is not supported "
        + "for ASCII protocol");
  }

