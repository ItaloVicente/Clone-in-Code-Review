    /**
     * This method must not be called outside the workbench.
     *
     * Utility method for handling status.
     */
    public static void handleStatus(IStatus status, int hint, Shell shell) {
    	StatusManager.getManager().handle(status, hint);
