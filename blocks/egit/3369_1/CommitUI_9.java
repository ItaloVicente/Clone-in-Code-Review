		indexDiff = new IndexDiff(repo, Constants.HEAD,
				IteratorService.createInitialIterator(repo));
		indexDiff.diff(jgitMonitor, counter.count, 0, NLS.bind(
				UIText.CommitActionHandler_repository, repo.getDirectory()
						.getPath()));

		includeList(indexDiff.getAdded(), indexChanges);
		includeList(indexDiff.getChanged(), indexChanges);
		includeList(indexDiff.getRemoved(), indexChanges);
		includeList(indexDiff.getMissing(), notIndexed);
		includeList(indexDiff.getModified(), notIndexed);
		includeList(indexDiff.getUntracked(), notTracked);
		if (monitor.isCanceled())
			throw new OperationCanceledException();
