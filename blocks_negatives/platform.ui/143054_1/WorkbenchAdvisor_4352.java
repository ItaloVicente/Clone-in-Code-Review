	 * Opens the workbench windows on startup. The default implementation tries
	 * to restore the previously saved workbench state using
	 * <code>IWorkbenchConfigurer.restoreWorkbenchState()</code>. If there
	 * was no previously saved state, or if the restore failed, then a
	 * first-time window is opened using
	 * <code>IWorkbenchConfigurer.openFirstTimeWindow</code>.
