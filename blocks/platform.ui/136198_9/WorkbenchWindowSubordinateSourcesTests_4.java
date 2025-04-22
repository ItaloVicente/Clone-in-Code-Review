		forceActive(window.getShell());
		assertWindowIsActive(window);

		window.setPerspectiveBarVisible(false);
		waitForJobs(100, 3000);
		processEvents();
		assertEquals(EvaluationResult.FALSE, with.evaluate(context));
