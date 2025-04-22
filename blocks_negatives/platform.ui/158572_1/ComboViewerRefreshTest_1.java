		exercise(new TestRunnable() {
			@Override
			public void run() {
				startMeasuring();
				for (int i = 0; i < 1000; i++) {
					viewer.refresh();
				}
				processEvents();
				stopMeasuring();
