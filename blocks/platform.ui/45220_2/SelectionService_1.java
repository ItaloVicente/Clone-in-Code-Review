			}
		}
	}

	public void notifyListeners(IWorkbenchPart activePart) {
		if (activePart != null) {
			ISelectionProvider selectionProvider = activePart.getSite().getSelectionProvider();
			if (selectionProvider != null) {
				ISelection selection = selectionProvider.getSelection();
