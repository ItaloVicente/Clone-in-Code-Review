	public static void showError(String message, Throwable throwable) {
		IStatus status = new Status(IStatus.ERROR, getPluginId(), message,
				throwable);
		StatusManager.getManager().handle(status, StatusManager.SHOW);
	}

