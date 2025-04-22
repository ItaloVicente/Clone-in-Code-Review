	private static void spinEventQueueToUpdateSplash(final Display display) {
		if (!display.isDisposed() && display.getThread() == Thread.currentThread()) {
			int safetyCounter = 0;
			while (display.readAndDispatch() && safetyCounter++ < 100) {
			}
		}
	}

