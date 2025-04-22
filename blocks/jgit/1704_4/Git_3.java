	public CreateBranchCommand branchCreate() {
		return new CreateBranchCommand(repo);
	}

	public DeleteBranchCommand branchDelete() {
		return new DeleteBranchCommand(repo);
	}

	public ListBranchCommand branchList() {
		return new ListBranchCommand(repo);
	}

	public RenameBranchCommand branchRename() {
		return new RenameBranchCommand(repo);
	}

