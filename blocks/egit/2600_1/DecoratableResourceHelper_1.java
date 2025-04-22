
	static String getRepositoryName(Repository repository) {
		String repoName = Activator.getDefault().getRepositoryUtil()
				.getRepositoryName(repository);
		RepositoryState state = repository.getRepositoryState();
		if (state != RepositoryState.SAFE)
			return repoName + '|' + state.getDescription();
		else
			return repoName;
	}

	static String getShortBranch(Repository repository) throws IOException {
		Ref head = repository.getRef(Constants.HEAD);
		if (head != null && !head.isSymbolic()) {
			String refString = Activator
					.getDefault()
					.getRepositoryUtil()
					.mapCommitToRef(repository, repository.getFullBranch(),
							false);
			if (refString != null) {
				return repository.getFullBranch().substring(0, 7)
						+ "... (" + refString + ")"; //$NON-NLS-1$ //$NON-NLS-2$
			} else
				return repository.getFullBranch().substring(0, 7) + "..."; //$NON-NLS-1$
		}

		return repository.getBranch();
	}
