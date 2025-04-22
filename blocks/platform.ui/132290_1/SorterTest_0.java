	private static void spinEventQueueToUpdateUi(Display display) {
		int safetyCounter = 0;
		while (display.readAndDispatch() && safetyCounter++ < 100) {
		}
	}

