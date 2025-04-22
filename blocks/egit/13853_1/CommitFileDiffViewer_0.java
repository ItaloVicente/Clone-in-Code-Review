	void selectFirstInterestingElement() {
		IStructuredContentProvider contentProvider = ((IStructuredContentProvider) getContentProvider());
		Object[] elements = contentProvider.getElements(getInput());
		for (final Object element : elements) {
			if (element instanceof FileDiff) {
				FileDiff fileDiff = (FileDiff) element;
				boolean marked = fileDiff
						.isMarked(FileDiffContentProvider.INTERESTING_MARK_TREE_FILTER_INDEX);
				if (marked) {
					setSelection(new StructuredSelection(fileDiff));
					return;
				}
			}
		}
	}

