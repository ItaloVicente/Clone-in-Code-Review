	private void updateLabelProviderFlags() {
		Object[] elements = ((IStructuredContentProvider) getContentProvider())
				.getElements(null);
		boolean all = true;
		for (int i = 0; i < elements.length; i++) {
			final FileDiff c = (FileDiff) elements[i];
			if (!c.isMarked(FileDiffContentProvider.INTERESTING_MARK_TREE_FILTER_INDEX)) {
				all = false;
				break;
			}
		}
		((FileDiffLabelProvider) getLabelProvider()).setAllInteresting(all);
	}

