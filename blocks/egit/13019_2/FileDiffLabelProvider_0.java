	public Color getForeground(Object element) {
		final FileDiff c = (FileDiff) element;
		if (!c.isMarked(FileDiffContentProvider.INTERESTING_MARK_TREE_FILTER_INDEX))
			return dimmedForegroundColor;
		else
			return null;
