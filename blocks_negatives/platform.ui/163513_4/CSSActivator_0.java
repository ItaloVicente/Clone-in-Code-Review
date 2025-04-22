	private LogService getLogger() {
		if (logTracker == null) {
			if (context == null) {
				return null;
			}
			logTracker = new ServiceTracker<>(context,
					LogService.class.getName(), null);
			logTracker.open();
		}
		return logTracker.getService();
	}

	public void log(int logError, String message) {
		LogService logger = getLogger();
		if (logger != null) {
			logger.log(logError, message);
		}
	}

	public void log(int logError, String message, Throwable e) {
		LogService logger = getLogger();
		if (logger != null) {
			logger.log(logError, message, e);
		}
	}

