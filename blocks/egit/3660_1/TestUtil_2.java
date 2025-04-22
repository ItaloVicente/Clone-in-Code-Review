	public static RevCommit getHeadCommit(Repository repository) throws Exception {
		RevCommit headCommit = null;
		ObjectId parentId = repository.resolve(Constants.HEAD);
		if (parentId != null)
			headCommit = new RevWalk(repository).parseCommit(parentId);
		return headCommit;
	}	
