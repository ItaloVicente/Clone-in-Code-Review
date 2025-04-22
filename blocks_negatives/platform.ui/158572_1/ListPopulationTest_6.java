		exercise(new TestRunnable() {
			@Override
			public void run() {
				list.removeAll();
				startMeasuring();
				for (String item : items) {
					list.add(item);
				}
				processEvents();
				stopMeasuring();
