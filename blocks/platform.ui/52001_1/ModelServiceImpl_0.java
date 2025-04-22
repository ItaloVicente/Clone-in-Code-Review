
	private void warn(String message) {
		Logger logger = appContext.get(Logger.class);
		if (logger != null) {
			logger.warn(message);
		}
	}
