		return UnitOfWork.run(repository, () -> {
			WorkingTreeIterator iterator = IteratorService
					.createInitialIterator(repository);
			if (iterator == null)
				return null; // workspace is closed
			IndexDiff newDiff = new IndexDiff(repository, Constants.HEAD,
					iterator);
			newDiff.diff(jgitMonitor, 0, 0, jobName);
			return new IndexDiffData(newDiff);
		});
