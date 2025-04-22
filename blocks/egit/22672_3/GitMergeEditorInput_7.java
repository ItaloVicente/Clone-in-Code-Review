	private RevCommit getRightCommit(RevWalk revWalk, Repository repository)
			throws InvocationTargetException {
		try {
			String target;
			if (repository.getRepositoryState().equals(RepositoryState.MERGING))
				target = Constants.MERGE_HEAD;
			else if (repository.getRepositoryState().equals(
					RepositoryState.CHERRY_PICKING))
				target = Constants.CHERRY_PICK_HEAD;
			else if (repository.getRepositoryState().equals(
					RepositoryState.REBASING_INTERACTIVE))
				target = readFile(repository.getDirectory(),
						RebaseCommand.REBASE_MERGE + File.separatorChar
								+ RebaseCommand.STOPPED_SHA);
