		this(new Repository[] { repository }, target, delete);
	}

	public BranchOperation(Repository[] repositories, String target,
			boolean delete) {
		this.repositories = repositories;
