		if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
			selectReveal(selection);
			selection = commonViewer.getSelection();
			if (selection != null && !selection.isEmpty()) {
				return true;
			}
		}
