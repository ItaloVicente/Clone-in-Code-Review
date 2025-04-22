		try {
			WorkingTreeIterator iterator = IteratorService
					.createInitialIterator(repository);
			newIndexDiff = new IndexDiff(repository, Constants.HEAD, iterator);
			newIndexDiff.diff(jgitMonitor, 0, 0, jobName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
