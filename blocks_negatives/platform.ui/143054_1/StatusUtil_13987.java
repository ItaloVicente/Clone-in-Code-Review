    /**
     * This method must not be called outside the workbench.
     *
     * Utility method for handling status.
     */
    public static void handleStatus(Throwable e, int hint) {
		StatusManager.getManager().handle(
				newStatus(WorkbenchPlugin.PI_WORKBENCH, e), hint);
