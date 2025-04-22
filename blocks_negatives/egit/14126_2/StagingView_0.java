	private String getSectionCount(TableViewer viewer) {
		int stagingEntryCount = ((StagingViewContentProvider) viewer
				.getContentProvider()).getStagingEntryCount();
		int itemCount = viewer.getTable().getItemCount();
		if (itemCount == stagingEntryCount)
			return Integer.toString(itemCount);
