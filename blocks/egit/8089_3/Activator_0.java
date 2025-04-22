	public static void handleIssue(int severity, String message, Throwable throwable,
			boolean show) {
		IStatus status = new Status(severity, getPluginId(), message,
				throwable);
		int style = StatusManager.LOG;
		if (show)
			style |= StatusManager.SHOW;
		StatusManager.getManager().handle(status, style);
	}

