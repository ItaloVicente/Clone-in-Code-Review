		CommitInfo headCommitInfo = CommitHelper.getHeadCommitInfo(repository);
		RevCommit previousCommit = headCommitInfo.getCommit();

		amendingCommitInRemoteBranch = isContainedInAnyRemoteBranch(previousCommit);
		previousCommitMessage = headCommitInfo.getCommitMessage();
		previousAuthor = headCommitInfo.getAuthor();
