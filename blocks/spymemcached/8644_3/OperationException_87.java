  private static final long serialVersionUID = 1524499960923239786L;

  private final OperationErrorType type;

  public OperationException() {
    super();
    type = OperationErrorType.GENERAL;
  }

  public OperationException(OperationErrorType eType, String msg) {
    super(msg);
    type = eType;
  }

  public OperationErrorType getType() {
    return type;
  }

  @Override
  public String toString() {
    String rv = null;
    if (type == OperationErrorType.GENERAL) {
      rv = "OperationException: " + type;
    } else {
      rv = "OperationException: " + type + ": " + getMessage();
    }
    return rv;
  }
