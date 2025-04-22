	private void checkHeadCommit(String author, String committer,
			String message) throws Exception {
		Repository repository = lookupRepository(repositoryFile);
		CommitInfo commitInfo = CommitHelper.getHeadCommitInfo(repository);
		assertEquals(author, commitInfo.getAuthor());
		assertEquals(committer, commitInfo.getCommitter());
		assertEquals(message, commitInfo.getCommitMessage());
	}

