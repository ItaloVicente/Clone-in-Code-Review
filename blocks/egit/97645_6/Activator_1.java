	public static IStatus warning(final String message, final Throwable thr) {
		return new Status(IStatus.WARNING, getPluginId(), 0, message, thr);
	}

	public static void logWarning(final String message, final Throwable thr) {
		getDefault().getLog().log(warning(message, thr));
	}

