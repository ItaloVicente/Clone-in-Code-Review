		window.setStatusLineVisible(!current);
		try {
			assertEquals(EvaluationResult.FALSE, with.evaluate(context));
		} catch (CoreException e) {
			fail(e.getMessage());
		}
