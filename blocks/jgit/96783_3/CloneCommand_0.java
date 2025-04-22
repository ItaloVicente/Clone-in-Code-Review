	private Thread registerShutdownHook() {
		Thread cleanupHook = new Thread() {

			@Override
			public void run() {
				cleanup();
			}
		};
		Runtime.getRuntime().addShutdownHook(cleanupHook);
		return cleanupHook;
	}

