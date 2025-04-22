     * This method must not be called outside the workbench.
     *
     * Utility method for handling status.
     */
	public static void handleStatus(String message, Throwable e, int hint,
			Shell shell) {
		StatusManager.getManager().handle(
				newStatus(WorkbenchPlugin.PI_WORKBENCH, message, e), hint);
