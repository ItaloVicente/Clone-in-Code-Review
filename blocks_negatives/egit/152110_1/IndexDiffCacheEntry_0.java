		WorkingTreeIterator iterator = IteratorService.createInitialIterator(repository);
		if (iterator == null)
		IndexDiff diffForChangedResources = new IndexDiff(repository,
				Constants.HEAD, iterator);
		diffForChangedResources.setFilter(PathFilterGroup
				.createFromStrings(treeFilterPaths));
		diffForChangedResources.diff(jgitMonitor, 0, 0, jobName);
		IndexDiffData previous = indexDiffData;
		if (previous == null) {
			return null;
