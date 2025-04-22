	/*
	 * Initialize the workbench colors.
	 *
	 * @since 3.0
	 */
	private void initializeColors() {
		StartupThreading.runWithoutExceptions(new StartupRunnable() {
			@Override
			public void runWithException() {
				WorkbenchColors.startup();
			}
		});
	}

