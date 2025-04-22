		try (ConfigScope scope = new ConfigScope(repository)) {
			WorkingTreeIterator iterator = IteratorService
					.createInitialIterator(repository);
			if (iterator == null)
				return null; // workspace is closed
			IndexDiff diffForChangedResources = new IndexDiff(repository,
					Constants.HEAD, iterator);
			diffForChangedResources.setFilter(
					PathFilterGroup.createFromStrings(treeFilterPaths));
			diffForChangedResources.diff(jgitMonitor, 0, 0, jobName);
			IndexDiffData previous = indexDiffData;
			if (previous == null) {
				return null;
			}
			return new IndexDiffData(previous, filesToUpdate, resourcesToUpdate,
					diffForChangedResources);
