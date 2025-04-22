		boolean merging = false;
		if (repository.getRepositoryState().equals(RepositoryState.MERGING)
				|| repository.getRepositoryState().equals(
						RepositoryState.MERGING_RESOLVED))
			merging = true;
		final boolean cherryPicking = repository.getRepositoryState().equals(RepositoryState.CHERRY_PICKING)
			|| repository.getRepositoryState().equals(RepositoryState.CHERRY_PICKING_RESOLVED);

		mapObjects();
		monitor.worked(1);

		writeRef();
		monitor.worked(1);

		switch (type) {
		case HARD:
			checkoutIndex();
			monitor.worked(1);
			if (merging)
				resetMerge();
			if (cherryPicking)
				resetCherryPick();
			monitor.worked(1);
			ProjectUtil.refreshValidProjects(validProjects, new SubProgressMonitor(
					monitor, 1));
			monitor.worked(1);
			break;

		case MIXED:
			resetIndex();
			monitor.worked(2);
			if (merging)
				resetMerge();
			if (cherryPicking)
				resetCherryPick();
			monitor.worked(1);
			break;

		case SOFT:
			monitor.worked(3);
		}
		monitor.done();
	}

	private void resetMerge() throws CoreException {
		try {
			repository.writeMergeHeads(null);
			repository.writeMergeCommitMsg(null);
		} catch (IOException e) {
			throw new TeamException(CoreText.ResetOperation_resetMergeFailed, e);
		}
	}
