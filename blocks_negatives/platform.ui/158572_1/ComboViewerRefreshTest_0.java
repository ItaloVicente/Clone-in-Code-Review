		exercise(new TestRunnable() {
			@Override
			public void run() {
				startMeasuring();
				viewer.refresh();
				processEvents();
				stopMeasuring();
			}
