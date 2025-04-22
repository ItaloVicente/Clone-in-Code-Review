  public void trace(Object message, Throwable exception) {
    log(Level.TRACE, message, exception);
  }

  public void trace(String message, Object... args) {
    if (isDebugEnabled()) {
      trace(String.format(message, args), getThrowable(args));
    }
  }

  public void trace(Object message) {
    trace(message, null);
  }

