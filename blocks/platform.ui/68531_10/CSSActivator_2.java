	public void log(int logError, String message, Throwable e) {
		LogService logger = getLogger();
		if (logger != null) {
			logger.log(logError, message, e);
		}
	}

