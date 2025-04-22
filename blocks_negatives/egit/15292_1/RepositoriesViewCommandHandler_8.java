		setBaseEnabled(true);
	}

	/**
	 * Retrieve the current selection. The global selection is used if the menu
	 * selection is not available.
	 *
	 * @param ctx
	 * @return the selection
	 */
	protected Object getSelection(IEvaluationContext ctx) {
		Object selection = ctx.getVariable(ISources.ACTIVE_MENU_SELECTION_NAME);
		if (selection == null || !(selection instanceof ISelection))
			selection = ctx.getVariable(ISources.ACTIVE_CURRENT_SELECTION_NAME);
		return selection;
