		exercise(new TestRunnable() {
			@Override
			public void run() {
				startMeasuring();
				for (int i = 0; i < 10; i++) {
					viewer.refresh();
					processEvents();
