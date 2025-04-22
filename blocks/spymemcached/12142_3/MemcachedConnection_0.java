  protected void addOperation(final String key, final Operation o,
      boolean validate) {
    if (validate) {
      StringUtils.validateKey(key);
      checkState();
    }

