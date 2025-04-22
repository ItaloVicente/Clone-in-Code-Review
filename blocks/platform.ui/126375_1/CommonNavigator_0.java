		if (selection != null && !selection.isEmpty() && selection instanceof IStructuredSelection) {
			selectReveal(selection);
			if (commonViewer.getSelection() != null && !commonViewer.getSelection().isEmpty()) {
				return true;
			}
		}
