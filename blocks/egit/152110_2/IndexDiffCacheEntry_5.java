		try (ConfigScope scope = new ConfigScope(repository)) {
			WorkingTreeIterator iterator = IteratorService
					.createInitialIterator(repository);
			if (iterator == null)
				return null; // workspace is closed
			newIndexDiff = new IndexDiff(repository, Constants.HEAD, iterator);
			newIndexDiff.diff(jgitMonitor, 0, 0, jobName);
			return new IndexDiffData(newIndexDiff);
		}
