
	private void initializeShallowCommits() throws IOException {
		if (shallowCommitsInitialized)
			return;

		shallowCommitsInitialized = true;

		for (ObjectId id : reader.getShallowCommits()) {
			final RevCommit commit = lookupCommit(id);
			commit.parents = new RevCommit[0];
		}
	}
