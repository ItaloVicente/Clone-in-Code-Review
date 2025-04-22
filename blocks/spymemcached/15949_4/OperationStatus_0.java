  public OperationStatus(boolean success, String msg, ErrorCode ec) {
    this(success, msg, ec, null);
  }

  public OperationStatus(boolean success, String msg, ErrorCode ec,
        Throwable t) {
