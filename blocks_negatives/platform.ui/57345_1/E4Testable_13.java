			Runnable runnable = new Runnable() {
				@Override
				public void run() {
						waitForEarlyStartup();
					}
					getTestHarness().runTests();
