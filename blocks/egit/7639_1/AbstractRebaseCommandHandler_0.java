
	protected IEditorInput getEditorInput(IEvaluationContext ctx) {
		Object editorInput = ctx.getVariable(ISources.ACTIVE_MENU_SELECTION_NAME);
		if (editorInput instanceof IEditorInput)
			return (IEditorInput) editorInput;

		return null;
	}

