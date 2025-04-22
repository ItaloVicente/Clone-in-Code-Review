  String getName();

  boolean isDebugEnabled();

  boolean isInfoEnabled();

  void log(Level level, Object message, Throwable exception);

  void log(Level level, Object message);

  void debug(Object message, Throwable exception);

  void debug(Object message);

  void debug(String message, Object... args);

  void info(Object message, Throwable exception);

  void info(Object message);

  void info(String message, Object... args);

  void warn(Object message, Throwable exception);

  void warn(Object message);

  void warn(String message, Object... args);

  void error(Object message, Throwable exception);

  void error(Object message);

  void error(String message, Object... args);

  void fatal(Object message, Throwable exception);

  void fatal(Object message);

  void fatal(String message, Object... args);
