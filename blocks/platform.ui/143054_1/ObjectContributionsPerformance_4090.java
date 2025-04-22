				"testObjectContributions");
		tagIfNecessary("UI - " + selection.size() + " contribution(s)",
				Dimension.ELAPSED_PROCESS);
		startMeasuring();
		for (int i = 0; i < 5000; i++) {
			tests.assertPopupMenus("1", new String[] { "bogus" }, selection,
					null, false);
		}
		stopMeasuring();
		commitMeasurements();
		assertPerformance();
