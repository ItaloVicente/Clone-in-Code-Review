
	void log(LogLevel level, String message, Object... args) {
		Logger log = this.logger;
		if (log != null) {
			switch (level) {
			case ERROR:
				log.error(message, args);
				break;
			case WARN:
				log.warn(message, args);
				break;
			case INFO:
				log.info(message, args);
				break;
			case DEBUG:
				log.debug(message, args);
				break;
			case AUDIT:
				log.audit(message, args);
				break;
			case TRACE:
				log.trace(message, args);
				break;
			}
		} else {
			if (LogLevel.ERROR == level) {
				System.err.println(MessageFormat.format(message, args));
			} else {
				System.out.println(MessageFormat.format(message, args));
			}
		}
	}

	@Reference(cardinality = ReferenceCardinality.OPTIONAL, policy = ReferencePolicy.DYNAMIC)
	public void setLogger(LoggerFactory factory) {
		this.factory = factory;
		this.logger = factory.getLogger(getClass());
	}

	public void unsetLogger(LoggerFactory loggerFactory) {
		if (this.factory == loggerFactory) {
			this.factory = null;
			this.logger = null;
		}
	}
