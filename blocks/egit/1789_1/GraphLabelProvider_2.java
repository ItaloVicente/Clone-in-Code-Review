	private PersonIdent committerOf(final RevCommit c) {
		if (lastCommit != c) {
			lastCommit = c;
			lastAuthor = c.getAuthorIdent();
			lastCommitter = c.getCommitterIdent();
		}
		return lastCommitter;
	}

