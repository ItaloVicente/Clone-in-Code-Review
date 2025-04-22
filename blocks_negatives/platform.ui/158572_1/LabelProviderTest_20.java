		exercise(new TestRunnable() {
			@Override
			public void run() {
				startMeasuring();
				for (int i = 0; i < ITEM_COUNT / 5; i++) {
					tree.setTopItem(tree.getItem(i * 5));
					processEvents();
				}
				stopMeasuring();
