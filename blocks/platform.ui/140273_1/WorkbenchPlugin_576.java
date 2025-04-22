	}

	public static void log(String message) {
		getDefault().getLog().log(StatusUtil.newStatus(IStatus.ERROR, message, null));
	}

	public static void log(Throwable t) {
