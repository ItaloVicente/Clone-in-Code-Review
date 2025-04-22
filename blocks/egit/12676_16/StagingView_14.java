	private String getSectionCount(TreeViewer viewer) {
		StagingViewContentProvider contentProvider = getContentProvider(viewer);
		int count = contentProvider.getCount();
		int shownCount = contentProvider.getShownCount();
		if (shownCount == count)
			return Integer.toString(count);
