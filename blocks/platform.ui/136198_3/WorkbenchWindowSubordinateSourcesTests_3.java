		waitOnShell(window.getShell());
		assertWindowIsActive(window);

		window.setStatusLineVisible(false);
		processEvents();
		assertEquals(EvaluationResult.FALSE, with.evaluate(context));
