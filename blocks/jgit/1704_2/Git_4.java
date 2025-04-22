	public CreateBranchCommand branchCreate() {
		return new CreateBranchCommand(repo);
	}

	public DeleteBranchCommand branchDelete() {
		return new DeleteBranchCommand(repo);
	}

