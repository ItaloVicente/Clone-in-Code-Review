	private void updateAuthorAndCommitter(Repository repository) {
		final UserConfig config = repository.getConfig().get(UserConfig.KEY);

		String author;
		if (repository.getRepositoryState() == RepositoryState.CHERRY_PICKING_RESOLVED)
			author = getCherryPickOriginalAuthor(repository);
		else {
			author = config.getAuthorName();
			final String authorEmail = config.getAuthorEmail();
		}

		String committer = config.getCommitterName();
		final String committerEmail = config.getCommitterEmail();

		authorText.setText(author);
		committerText.setText(committer);
	}

	private void updateCommitMessage(Repository repository) {
		if (repository.getRepositoryState() == RepositoryState.MERGING_RESOLVED
				|| repository.getRepositoryState() == RepositoryState.CHERRY_PICKING_RESOLVED)
			commitMessageText.setText(getMergeResolveMessage(repository));
	}

	private String getCherryPickOriginalAuthor(Repository mergeRepository) {
		try {
			ObjectId cherryPickHead = mergeRepository.readCherryPickHead();
			PersonIdent author = new RevWalk(mergeRepository).parseCommit(cherryPickHead).getAuthorIdent();
		} catch (IOException e) {
			Activator.handleError(UIText.CommitAction_errorRetrievingCommit, e,
					true);
			throw new IllegalStateException(e);
		}
	}

	private String getMergeResolveMessage(Repository mergeRepository) {
		File mergeMsg = new File(mergeRepository.getDirectory(), Constants.MERGE_MSG);
		FileReader reader;
