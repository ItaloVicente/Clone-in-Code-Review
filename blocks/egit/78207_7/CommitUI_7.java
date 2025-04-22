		WorkingTreeIterator it = IteratorService
				.createInitialIterator(repository);
		if (it == null) {
			throw new OperationCanceledException();
		}
		IndexDiff diff = new IndexDiff(repository, Constants.HEAD, it);
		diff.diff(jgitMonitor, counter.count, 0,
				NLS.bind(UIText.CommitActionHandler_repository,
						repository.getDirectory().getPath()));
		if (progress.isCanceled()) {
			throw new OperationCanceledException();
		}
		return diff;
	}
