	/**
	 * This method must not be called outside the workbench.
	 *
	 * Utility method for handling status.
	 */
	public static void handleStatus(String message, int hint, Shell shell) {
		handleStatus(message, null, hint);
	}
