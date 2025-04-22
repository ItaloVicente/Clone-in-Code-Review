		return new BranchOperationUI(repository, MODE_BRANCH);
	}

	public static BranchOperationUI create(Repository repository) {
		BranchOperationUI op = new BranchOperationUI(repository, MODE_CREATE);
		return op;
	}

	public static BranchOperationUI checkout(Repository repository) {
		return new BranchOperationUI(repository, MODE_CHECKOUT);
