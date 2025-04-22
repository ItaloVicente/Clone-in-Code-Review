		return getSelection(evaluationContext);
	}

	private static IStructuredSelection convertSelection(ISelection selection) {
		if (selection instanceof IStructuredSelection)
			return (IStructuredSelection) selection;
		else if (selection instanceof ITextSelection)
			return getSelectionFromEditorInput(getEvaluationContext());
		return StructuredSelection.EMPTY;
	}

	private static IStructuredSelection getSelection(IEvaluationContext context) {
		if (context == null)
			return StructuredSelection.EMPTY;

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
		if (!(object instanceof IEditorInput)) {
			Object editor = context.getVariable(ISources.ACTIVE_EDITOR_NAME);
			if (editor instanceof IEditorPart)
				object = ((IEditorPart) editor).getEditorInput();
		}

		if (object instanceof IEditorInput) {
			IEditorInput editorInput = (IEditorInput) object;
			IResource resource = ResourceUtil.getResource(editorInput);
			if (resource != null)
				return new StructuredSelection(resource);
		}

		return StructuredSelection.EMPTY;
