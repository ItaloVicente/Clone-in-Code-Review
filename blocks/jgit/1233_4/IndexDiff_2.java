	public IndexDiff(Repository repository
			WorkingTreeIterator workingTreeIterator) throws IOException {
		this.repository = repository;
		ObjectId objectId = repository.resolve(revstr);
		tree = new RevWalk(repository).parseTree(objectId);
		this.initialWorkingTreeIterator = workingTreeIterator;
