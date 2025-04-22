	protected ISelection getSelection() {
		if (lastSelection != null) {
			return lastSelection;
		}
		return getSite().getPage().getSelection();
	}

	protected Object getMostFittingInput(Object object) {
		ISelection selection = getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			HistoryPageInput mostFittingInput = SelectionUtils
					.getMostFittingInput(structuredSelection, object);
			if (mostFittingInput != null) {
				return mostFittingInput;
			}
		}
		return object;
	}

