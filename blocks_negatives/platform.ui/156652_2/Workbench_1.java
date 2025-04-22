	/**
	 *
	 */
	private void initializeWorkbenchImages() {
		StartupThreading.runWithoutExceptions(new StartupRunnable() {
			@Override
			public void runWithException() {
				WorkbenchImages.getDescriptors();
			}
		});
	}

