		WorkingTreeIterator it = IteratorService.createInitialIterator(repo);
		if (it == null)
		indexDiff = new IndexDiff(repo, Constants.HEAD, it);
		indexDiff.diff(jgitMonitor, counter.count, 0, NLS.bind(
				UIText.CommitActionHandler_repository, repo.getDirectory()
						.getPath()));
