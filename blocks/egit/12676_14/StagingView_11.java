	private String getSectionCount(TreeViewer viewer) {
		int count = ((StagingViewContentProvider) viewer.getContentProvider())
				.getCount();
		String filter = null;
		if (filterText != null)
			filter = filterText.getText().trim();
		int shownCount = ((StagingViewContentProvider) viewer
				.getContentProvider()).getShownCount(filter);
		if (shownCount == count)
			return Integer.toString(count);
