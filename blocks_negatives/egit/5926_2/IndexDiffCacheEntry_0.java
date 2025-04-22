		final IndexDiff diffForChangedResources;
		try {
			WorkingTreeIterator iterator = IteratorService
					.createInitialIterator(repository);
			diffForChangedResources = new IndexDiff(repository, Constants.HEAD,
					iterator);
			diffForChangedResources.setFilter(PathFilterGroup
					.createFromStrings(filesToUpdate));
			diffForChangedResources.diff(jgitMonitor, 0, 0, jobName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
