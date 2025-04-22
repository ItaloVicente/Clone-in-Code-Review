		IndexDiff newIndexDiff;
		WorkingTreeIterator iterator = IteratorService
				.createInitialIterator(repository);
		if (iterator == null)
		newIndexDiff = new IndexDiff(repository, Constants.HEAD, iterator);
		newIndexDiff.diff(jgitMonitor, 0, 0, jobName);
		return new IndexDiffData(newIndexDiff);
