
	void setInterestingPaths(Set<String> interestingPaths) {
		((FileDiffContentProvider) getContentProvider()).setInterestingPaths(interestingPaths);
	}

	private void revealFirstInterestingElement() {
		IStructuredContentProvider contentProvider = ((IStructuredContentProvider) getContentProvider());
		Object[] elements = contentProvider.getElements(getInput());
		if (elements.length <= 1)
			return;

		for (final Object element : elements) {
			if (element instanceof FileDiff) {
				FileDiff fileDiff = (FileDiff) element;
				boolean marked = fileDiff.isMarked(FileDiffContentProvider.INTERESTING_MARK_TREE_FILTER_INDEX);
				if (marked) {
					getTable().getDisplay().asyncExec(new Runnable() {
						public void run() {
							reveal(element);
						}
					});
					return;
				}
			}
		}
	}
