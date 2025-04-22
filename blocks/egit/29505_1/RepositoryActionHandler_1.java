
		Object selection = context
				.getVariable(ISources.ACTIVE_MENU_SELECTION_NAME);
		if (!(selection instanceof ISelection))
			selection = context
					.getVariable(ISources.ACTIVE_CURRENT_SELECTION_NAME);

		if (selection instanceof IStructuredSelection)
			return (IStructuredSelection) selection;
		else if (selection instanceof ITextSelection)
			return getSelectionFromEditorInput(context);
		return StructuredSelection.EMPTY;
	}

	private static IStructuredSelection getSelectionFromEditorInput(
			IEvaluationContext context) {
		Object object = context.getVariable(ISources.ACTIVE_EDITOR_INPUT_NAME);
		if (object instanceof IEditorInput) {
			IEditorInput editorInput = (IEditorInput) object;
			IResource resource = ResourceUtil.getResource(editorInput);
