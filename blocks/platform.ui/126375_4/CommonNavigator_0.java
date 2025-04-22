		if (selection != null && !selection.isEmpty() && selection instanceof IStructuredSelection) {
			selectReveal(selection);
			selection = commonViewer.getSelection();
			if (selection != null && !selection.isEmpty()) {
				return true;
			}
		}
