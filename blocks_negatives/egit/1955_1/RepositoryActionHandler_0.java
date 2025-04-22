		IHandlerService hsr = (IHandlerService) activeWorkbenchWindow
				.getService(IHandlerService.class);
		IEvaluationContext ctx = hsr.getCurrentState();
		Object selection = ctx.getVariable(ISources.ACTIVE_MENU_SELECTION_NAME);
		if (selection == null)
			selection = ctx.getVariable(ISources.ACTIVE_CURRENT_SELECTION_NAME);
