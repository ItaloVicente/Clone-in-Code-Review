
	/**
	 * @param message
	 * @param e
	 */
	public static void logError(String message, Throwable e) {
		handleError(message, e, false);
	}

	/**
	 * @param message
	 * @param e
	 */
	public static void error(String message, Throwable e) {
		handleError(message, e, false);
	}

	/**
	 * Creates an error status
	 *
	 * @param message
	 *            a localized message
	 * @param throwable
	 * @return a new Status object
	 */
	public static IStatus createErrorStatus(String message, Throwable throwable) {
		return new Status(IStatus.ERROR, getPluginId(), message, throwable);
	}

	/**
	 * Creates an error status
	 *
	 * @param message
	 *            a localized message
	 * @return a new Status object
	 */
	public static IStatus createErrorStatus(String message) {
		return new Status(IStatus.ERROR, getPluginId(), message);
	}

