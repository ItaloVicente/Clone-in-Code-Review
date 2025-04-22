	private void openCommit(ObjectId id) {
		try {
			RevCommit commit = new RevWalk(repository).parseCommit(id);
			CommitEditor.openQuiet(new RepositoryCommit(repository, commit));
		} catch (IOException e) {
			Activator.logError(UIText.MergeResultDialog_couldNotFindCommit, e);
		}
