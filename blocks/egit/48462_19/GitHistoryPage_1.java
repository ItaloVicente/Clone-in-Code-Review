	private IStructuredSelection getSelection() {
		if (selectionTracker != null
				&& selectionTracker.getSelection() != null) {
			return selectionTracker.getSelection();
		}
		ISelection selection = getSite().getPage().getSelection();
		if (selection != null) {
			return SelectionUtils.getStructuredSelection(selection);
		}
		return null;
	}

	private Object getMostFittingInput(Object object) {
		IStructuredSelection selection = getSelection();
		if (selection != null && !selection.isEmpty()) {
			HistoryPageInput mostFittingInput = SelectionUtils
					.getMostFittingInput(selection, object);
			if (mostFittingInput != null) {
				return mostFittingInput;
			}
		}
		return object;
	}

