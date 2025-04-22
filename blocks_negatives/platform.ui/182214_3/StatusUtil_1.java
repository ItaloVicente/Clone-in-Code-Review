	/**
	 * This method must not be called outside the workbench.
	 *
	 * Utility method for handling status.
	 */
	public static void handleStatus(IStatus status, String message, int hint, Shell shell) {
		StatusManager.getManager().handle(newStatus(status, message), hint);
	}

