		this.commitId = null;
	}

	public BranchOperation(Repository repository, ObjectId commit) {
		this.repository = repository;
		this.refName = null;
		this.commitId = commit;
