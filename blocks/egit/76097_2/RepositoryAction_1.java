		if (event.getApplicationContext() instanceof IEvaluationContext) {
			IEvaluationContext context = (IEvaluationContext) event
					.getApplicationContext();

			context.addVariable(ISources.ACTIVE_CURRENT_SELECTION_NAME,
					mySelection);

			Object menuSelection = context
					.getVariable(ISources.ACTIVE_MENU_SELECTION_NAME);
			if (menuSelection != null) {
				context.addVariable(ISources.ACTIVE_MENU_SELECTION_NAME,
						menuSelection);
			}
		}

