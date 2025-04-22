		if (selection instanceof ITextSelection) {
			IEvaluationContext evaluationContext = getEvaluationContext();
			if (evaluationContext == null) {
				return StructuredSelection.EMPTY;
			}
			return getSelectionFromEditorInput(evaluationContext);
		} else if (selection instanceof IStructuredSelection) {
