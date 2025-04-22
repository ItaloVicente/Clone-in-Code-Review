	public boolean isCommitWithoutFilesAllowed() {
		return isCommitWithoutFilesAllowed(repository);
	}

	public static boolean isCommitWithoutFilesAllowed(Repository repository) {
		RepositoryState state = repository.getRepositoryState();
		return state == RepositoryState.MERGING_RESOLVED
				|| state == RepositoryState.CHERRY_PICKING_RESOLVED;
	}

