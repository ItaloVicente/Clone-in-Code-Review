		StartupThreading.runWithoutExceptions(new StartupRunnable() {

			@Override
			public void runWithException() {
				WorkbenchImages.getImageRegistry();
			}
		});
