		forceActive(window.getShell());
		assertWindowIsActive(window);

		window.setCoolBarVisible(false);
		processEvents();
		assertEquals(EvaluationResult.FALSE, with.evaluate(context));
