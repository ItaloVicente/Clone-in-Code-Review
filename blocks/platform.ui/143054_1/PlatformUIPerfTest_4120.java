	public void testCreateAndDisposeDisplayX100() {
		for (int i = 0; i < 10; ++i ) {
			startMeasuring();
			for (int j = 0; j < 100; ++j) {
				Display display = PlatformUI.createDisplay();
				display.dispose();
				assertTrue(display.isDisposed());
			}
			stopMeasuring();
		}

		commitMeasurements();
		assertPerformance();
	}

	public void testRunAndShutdownWorkbench() {
		Display display = PlatformUI.createDisplay();

		for (int i = 0; i < 10; ++i ) {
			startMeasuring();
			int code = PlatformUI.createAndRunWorkbench(display, new WorkbenchAdvisorObserver(2));
			stopMeasuring();

			assertEquals(PlatformUI.RETURN_OK, code);
		}

		display.dispose();
		assertTrue(display.isDisposed());

		commitMeasurements();
		assertPerformance();
	}
