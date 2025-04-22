	public static IStatus createStatus(int severity, int aCode, String aMessage, Throwable exception) {
		return new Status(severity, PLUGIN_ID, aCode, aMessage, exception);
	}

	public static IStatus createErrorStatus(int aCode, String aMessage, Throwable exception) {
		return createStatus(IStatus.ERROR, aCode, aMessage, exception);
	}
