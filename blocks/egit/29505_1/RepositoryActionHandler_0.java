	private static IStructuredSelection convertSelection(ISelection selection) {
		if (selection instanceof IStructuredSelection)
			return (IStructuredSelection) selection;
		else if (selection instanceof ITextSelection)
			return getSelectionFromEditorInput(getEvaluationContext());
		return StructuredSelection.EMPTY;
	}

	private static IStructuredSelection getSelection(IEvaluationContext context) {
		if (context == null)
