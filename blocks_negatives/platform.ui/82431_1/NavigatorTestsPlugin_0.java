	public static void log(String message) {
		getDefault().getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, 0, message, null));
		System.err.println(message);
	}

	/**
	 * Logs errors.
	 */
	public static void log(String message, IStatus status) {
		if (message != null) {
			getDefault().getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, 0, message, null));
		}
		getDefault().getLog().log(status);
		System.err.println(status.getMessage());
	}


	public static void logError(int aCode, String aMessage, Throwable anException) {
		getDefault().getLog().log(createErrorStatus(aCode, aMessage, anException));
	}

	public static void log(int severity, int aCode, String aMessage, Throwable exception) {
		log(createStatus(severity, aCode, aMessage, exception));
	}
