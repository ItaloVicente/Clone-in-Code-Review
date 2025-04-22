			commitOperation = new CommitOperation(repository,
					commitMessageComponent.getAuthor(),
					commitMessageComponent.getCommitter(),
					commitMessageComponent.getCommitMessage()) {

				protected RevCommit commit() throws TeamException {
					RevCommit commit = super.commit();
					openNewCommit(commit);
					return commit;
