	private static IStatus toStatus(int severity, String message,
			Throwable throwable) {
		Throwable exc = throwable;
		while (exc instanceof InvocationTargetException) {
			String msg = exc.getLocalizedMessage();
			if (msg != null && !msg.isEmpty()) {
				break;
			}
			Throwable cause = throwable.getCause();
			if (cause == null) {
				break;
			}
			exc = cause;
		}
		if (message == null || message.isEmpty()) {
			message = exc.getLocalizedMessage();
		}
		return new Status(severity, getPluginId(), message, exc);
	}

