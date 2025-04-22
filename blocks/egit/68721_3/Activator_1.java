	public static void logError(String message, Throwable e) {
		handleError(message, e, false);
	}

	public static void error(String message, Throwable e) {
		handleError(message, e, false);
	}

	public static IStatus createErrorStatus(String message,
			Throwable throwable) {
		return toStatus(IStatus.ERROR, message, throwable);
	}

	public static IStatus createErrorStatus(String message) {
		return toStatus(IStatus.ERROR, message, null);
	}

