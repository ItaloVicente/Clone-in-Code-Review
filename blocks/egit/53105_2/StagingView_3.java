
		StagingViewContentProvider stagedContentProvider = getContentProvider(
				viewer);
		int count = stagedContentProvider.getCount();
		if (count > 10000) {
			disableAutoExpand(viewer);
		}

