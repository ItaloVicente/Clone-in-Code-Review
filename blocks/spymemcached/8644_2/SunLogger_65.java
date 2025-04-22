    switch (level == null ? Level.FATAL : level) {
    case DEBUG:
      sLevel = java.util.logging.Level.FINE;
      break;
    case INFO:
      sLevel = java.util.logging.Level.INFO;
      break;
    case WARN:
      sLevel = java.util.logging.Level.WARNING;
      break;
    case ERROR:
      sLevel = java.util.logging.Level.SEVERE;
      break;
    case FATAL:
      sLevel = java.util.logging.Level.SEVERE;
      break;
    default:
      sLevel = java.util.logging.Level.SEVERE;
      sunLogger.log(sLevel, "Unhandled log level:  " + level
          + " for the following message");
    }
