
		StagingViewContentProvider stagedContentProvider = getContentProvider(
				viewer);
		int count = stagedContentProvider.getCount();
		updateAutoExpand(viewer, count);

