	/**
	 * Adds a window listener to the workbench to keep track of
	 * opened test windows.
	 */
	private void addWindowListener() {
		windowListener = new TestWindowListener();
		fWorkbench.addWindowListener(windowListener);
	}

	/**
	 * Removes the listener added by <code>addWindowListener</code>.
	 */
	private void removeWindowListener() {
		if (windowListener != null) {
			fWorkbench.removeWindowListener(windowListener);
		}
	}

