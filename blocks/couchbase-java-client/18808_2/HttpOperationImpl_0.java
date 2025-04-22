
  @Override
  public String toString() {
    StringBuilder rv = new StringBuilder(HttpOperationImpl.class.getName());
    rv.append(" request:").append(getRequest())
      .append(" has errored: ").append(hasErrored())
      .append(" is  cancelled: ").append(isCancelled())
      .append(" is timed out: ").append(isTimedOut());
    if (null != getException()) {
      rv.append(" with contained exception: ")
        .append(getException().getClass().getName())
        .append(": ").append(getException().getMessage());
    }
    return rv.toString();

  }


