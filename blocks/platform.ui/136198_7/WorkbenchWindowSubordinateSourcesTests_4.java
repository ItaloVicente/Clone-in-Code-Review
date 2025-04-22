		forceActive(window.getShell());
		assertWindowIsActive(window);

		window.setPerspectiveBarVisible(false);
		processEvents();
		assertEquals(EvaluationResult.FALSE, with.evaluate(context));
