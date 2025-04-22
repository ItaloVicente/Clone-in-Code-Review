	private static RevCommit getParentCommit(RevCommit commit) {
		if (commit.getParents().length > 0)
			return commit.getParents()[0];
		else
			return null;
	}

