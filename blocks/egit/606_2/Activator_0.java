	public static void handleError(String message, Throwable throwable,
			boolean show) {
		IStatus status = new Status(IStatus.ERROR, getPluginId(), message,
				throwable);
		int style = StatusManager.LOG;
		if (show)
			style |= StatusManager.SHOW;
		StatusManager.getManager().handle(status, style);
	}

