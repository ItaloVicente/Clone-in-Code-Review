	public ListBranchCommand branchList() {
		return new ListBranchCommand(repo);
	}

	public RenameBranchCommand branchRename() {
		return new RenameBranchCommand(repo);
	}

