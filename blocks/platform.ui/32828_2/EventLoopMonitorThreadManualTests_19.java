		assertEquals("Did not log expected number of freeze events,",
				NUM_UI_STACK_MEASUREMENTS + NUM_ALL_STACKS_MEASUREMENTS,
				logger.getLoggedEvents().size());
		assertTrue(String.format("Relative overhead of monitoring with stack traces of the UI "
				+ "thread was %.3f%% (allowed < %.3f%%).",
				worstRelativeDiffOneThread * 100,
				maxRelativeIncreaseOneStackAllowed * 100),
				worstRelativeDiffOneThread <= maxRelativeIncreaseOneStackAllowed);
		assertTrue(String.format("Relative overhead of monitoring with stack traces of all "
				+ "threads was %.3f%% (allowed < %.3f%%).",
				worstRelativeDiffAllThreads * 100,
				maxRelativeIncreaseAllStacksAllowed * 100),
				worstRelativeDiffAllThreads <= maxRelativeIncreaseAllStacksAllowed);
