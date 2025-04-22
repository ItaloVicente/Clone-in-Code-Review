
	void initializeShallowCommits() throws IOException {
		if (shallowCommitsInitialized)
			throw new IllegalStateException(JGitText.get().outputHasAlreadyBeenStarted);

		shallowCommitsInitialized = true;

		for (ObjectId id : reader.getShallowCommits())
			lookupCommit(id).parents = RevCommit.NO_PARENTS;
	}
