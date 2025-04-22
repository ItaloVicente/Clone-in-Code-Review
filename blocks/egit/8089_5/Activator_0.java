		handleIssue(IStatus.ERROR, message, throwable, show);
	}

	public static void handleIssue(int severity, String message, Throwable throwable,
			boolean show) {
		IStatus status = new Status(severity, getPluginId(), message,
