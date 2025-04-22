	private static void spinEventQueueToUpdateUi(final Display display) {
		int safetyCounter = 0;
		while (display.readAndDispatch() && safetyCounter++ < 100) {
		}
	}

