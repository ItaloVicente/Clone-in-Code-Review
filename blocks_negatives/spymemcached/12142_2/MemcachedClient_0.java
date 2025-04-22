  /**
   * (internal use) Add a raw operation to a numbered connection. This method is
   * exposed for testing.
   *
   * @param which server number
   * @param op the operation to perform
   * @return the Operation
   */
  Operation addOp(final String key, final Operation op) {
    StringUtils.validateKey(key);
    mconn.checkState();
    mconn.addOperation(key, op);
    return op;
  }

