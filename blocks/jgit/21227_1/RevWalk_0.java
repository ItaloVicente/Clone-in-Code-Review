	public void assumeShallow(Collection<? extends ObjectId> ids)
			throws IOException {
		if (!shallowCommitsInitialized)
			initializeShallowCommits();
		for (ObjectId id : ids)
			lookupCommit(id).parents = RevCommit.NO_PARENTS;
	}

