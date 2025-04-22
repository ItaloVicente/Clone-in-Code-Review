		forceActive(window.getShell());
		assertWindowIsActive(window);

		window.setCoolBarVisible(false);
		waitForJobs(100, 3000);
		processEvents();
		assertEquals(EvaluationResult.FALSE, with.evaluate(context));
