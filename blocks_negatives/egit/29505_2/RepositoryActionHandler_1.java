	private IStructuredSelection convertSelection(IEvaluationContext aContext,
			Object aSelection) {
		IEvaluationContext ctx;
		if (aContext == null && aSelection == null)
			return StructuredSelection.EMPTY;
		else
			ctx = aContext;
		Object selection;
		if (aSelection == null && ctx != null) {
			selection = ctx.getVariable(ISources.ACTIVE_MENU_SELECTION_NAME);
			if (!(selection instanceof ISelection))
				selection = ctx
						.getVariable(ISources.ACTIVE_CURRENT_SELECTION_NAME);
		} else if (aSelection != null)
			selection = aSelection;
		else
