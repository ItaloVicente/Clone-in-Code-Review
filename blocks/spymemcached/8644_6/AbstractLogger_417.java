  protected AbstractLogger(String nm) {
    super();
    if (nm == null) {
      throw new NullPointerException("Logger name may not be null.");
    }
    name = nm;
  }

  public String getName() {
    return (name);
  }

  public Throwable getThrowable(Object[] args) {
    Throwable rv = null;
    if (args.length > 0) {
      if (args[args.length - 1] instanceof Throwable) {
        rv = (Throwable) args[args.length - 1];
      }
    }
    return rv;
  }

  public abstract boolean isDebugEnabled();

  public abstract boolean isInfoEnabled();

  public void debug(Object message, Throwable exception) {
    log(Level.DEBUG, message, exception);
  }

  public void debug(String message, Object... args) {
    if (isDebugEnabled()) {
      debug(String.format(message, args), getThrowable(args));
    }
  }

  public void debug(Object message) {
    debug(message, null);
  }

  public void info(Object message, Throwable exception) {
    log(Level.INFO, message, exception);
  }

  public void info(String message, Object... args) {
    if (isInfoEnabled()) {
      info(String.format(message, args), getThrowable(args));
    }
  }

  public void info(Object message) {
    info(message, null);
  }

  public void warn(Object message, Throwable exception) {
    log(Level.WARN, message, exception);
  }

  public void warn(String message, Object... args) {
    warn(String.format(message, args), getThrowable(args));
  }

  public void warn(Object message) {
    warn(message, null);
  }

  public void error(Object message, Throwable exception) {
    log(Level.ERROR, message, exception);
  }

  public void error(String message, Object... args) {
    error(String.format(message, args), getThrowable(args));
  }

  public void error(Object message) {
    error(message, null);
  }

  public void fatal(Object message, Throwable exception) {
    log(Level.FATAL, message, exception);
  }

  public void fatal(String message, Object... args) {
    fatal(String.format(message, args), getThrowable(args));
  }

  public void fatal(Object message) {
    fatal(message, null);
  }

  public void log(Level level, Object message) {
    log(level, message, null);
  }

  public abstract void log(Level level, Object message, Throwable e);
