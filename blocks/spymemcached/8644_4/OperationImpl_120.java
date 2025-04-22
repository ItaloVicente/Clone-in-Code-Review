  protected final OperationStatus matchStatus(String line,
      OperationStatus... statii) {
    OperationStatus rv = null;
    for (OperationStatus status : statii) {
      if (line.equals(status.getMessage())) {
        rv = status;
      }
    }
    if (rv == null) {
      rv = new OperationStatus(false, line);
    }
    return rv;
  }
