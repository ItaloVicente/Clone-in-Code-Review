	 * This method is called from a ShellListener associated with the window,
	 * for example when the user clicks the window's close button. It is not
	 * called when the window is being closed for other reasons, such as if the
	 * user exits the workbench via the {@link ActionFactory#QUIT} action.
	 * Clients must not call this method directly (although super calls are
	 * okay). If this method returns <code>false</code>, then the user's
	 * request to close the shell is ignored. This gives the workbench advisor
	 * an opportunity to query the user and/or veto the closing of a window
	 * under some circumstances.
