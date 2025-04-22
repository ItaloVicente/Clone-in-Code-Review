	private int getGeneration(RevCommit commit) throws MissingObjectException
			IncorrectObjectTypeException
		int generation = Constants.COMMIT_GENERATION_UNKNOWN;
		if (commit instanceof RevCommitCG) {
			commit.parseHeaders(this);
			generation = commit.getGeneration();
		}
		return generation;
	}

