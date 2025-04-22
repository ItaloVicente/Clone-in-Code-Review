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
	}

	/**
	 * Extracts the editor input from the given context.
	 *
	 * @param ctx the context
	 * @return the editor input for the given context or <code>null</code> if not available
	 * @since 2.1
	 */
	protected IEditorInput getActiveEditorInput(IEvaluationContext ctx) {
		Object editorInput = ctx.getVariable(ISources.ACTIVE_EDITOR_INPUT_NAME);
		if (editorInput instanceof IEditorInput)
			return (IEditorInput) editorInput;

		return null;
	}

