		exercise(new TestRunnable() {
			@Override
			public void run() {
				list.removeAll();
				startMeasuring();
				list.setItems(items);
				processEvents();
				stopMeasuring();
			}
