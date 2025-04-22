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
