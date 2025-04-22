		final CommitOperation commitOperation = new CommitOperation(
				commitDialog.getSelectedFiles(), notIndexed, notTracked,
				commitDialog.getAuthor(), commitDialog.getCommitter(),
				commitDialog.getCommitMessage());
		if (commitDialog.isAmending()) {
			commitOperation.setAmending(true);
			commitOperation.setRepos(repos);
