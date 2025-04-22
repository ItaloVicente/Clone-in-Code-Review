	void addSkipCommit(AnyObjectId commit) {
		skipCommits.add(commit.copy());
	}

	boolean isSkippedCommit(AnyObjectId commit) {
		return skipCommits.contains(commit);
	}

