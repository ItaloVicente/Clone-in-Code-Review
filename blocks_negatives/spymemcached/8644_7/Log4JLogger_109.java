		switch(level == null ? Level.FATAL : level) {
			case DEBUG:
				pLevel=org.apache.log4j.Level.DEBUG;
				break;
			case INFO:
				pLevel=org.apache.log4j.Level.INFO;
				break;
			case WARN:
				pLevel=org.apache.log4j.Level.WARN;
				break;
			case ERROR:
				pLevel=org.apache.log4j.Level.ERROR;
				break;
			case FATAL:
				pLevel=org.apache.log4j.Level.FATAL;
				break;
			default:
				pLevel=org.apache.log4j.Level.FATAL;
				l4jLogger.log("net.spy.compat.log.AbstractLogger", pLevel,
					"Unhandled log level:  " + level
						+ " for the following message", null);
		}
