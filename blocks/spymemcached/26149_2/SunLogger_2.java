
package net.spy.memcached.compat.log;

public class SLF4JLogger extends AbstractLogger {

  private final org.slf4j.Logger logger;

  public SLF4JLogger(String name) {
    super(name);
    logger = org.slf4j.LoggerFactory.getLogger(name);
  }

  @Override
  public boolean isTraceEnabled() {
    return logger.isTraceEnabled();
  }

  @Override
  public boolean isDebugEnabled() {
    return logger.isDebugEnabled();
  }

  @Override
  public boolean isInfoEnabled() {
    return logger.isInfoEnabled();
  }

  @Override
  public void log(Level level, Object message, Throwable e) {
    if(level == null) {
      level = Level.FATAL;
    }

    switch(level) {
    case TRACE:
      logger.trace(message.toString(), e);
      break;
    case DEBUG:
      logger.debug(message.toString(), e);
      break;
    case INFO:
      logger.info(message.toString(), e);
      break;
    case WARN:
      logger.warn(message.toString(), e);
      break;
    case ERROR:
      logger.error(message.toString(), e);
      break;
    case FATAL:
      logger.error(message.toString(), e);
      break;
    default:
      logger.error("Unhandled Logging Level: " + level
        + " with log message: " + message.toString(), e);
    }
  }

}
