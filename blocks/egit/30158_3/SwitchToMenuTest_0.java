	private void mockSelection(ISelection selection) {
		EvaluationContext context = new EvaluationContext(null, new Object());
		context.addVariable(ISources.ACTIVE_MENU_SELECTION_NAME, selection);
		when(handlerService.getCurrentState()).thenReturn(context);
	}

