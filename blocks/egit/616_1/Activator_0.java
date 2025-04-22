	public static void logError(String message, Throwable e) {
		handleError(message, e, false);
	}

	public static void error(String message, Throwable e) {
		handleError(message, e, false);
	}

