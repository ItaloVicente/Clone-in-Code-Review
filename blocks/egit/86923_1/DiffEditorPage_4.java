			currentFileDiffRange = null;
		}
		if (outlinePage != null) {
			outlinePage.setInput(getDocumentProvider().getDocument(input));
			if (currentFileDiffRange != null) {
				outlinePage.setSelection(
						new StructuredSelection(currentFileDiffRange));
			}
		}
	}

	@Override
	protected void doSetSelection(ISelection selection) {
		if (!selection.isEmpty() && selection instanceof StructuredSelection) {
			Object selected = ((StructuredSelection) selection)
					.getFirstElement();
			if (selected instanceof FileDiffRegion) {
				FileDiffRegion newRange = (FileDiffRegion) selected;
				if (!newRange.equals(currentFileDiffRange)) {
					currentFileDiffRange = newRange;
					selectAndReveal(newRange.getOffset(), 0);
				}
				return;
			}
