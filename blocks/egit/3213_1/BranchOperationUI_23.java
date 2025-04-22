	public static BranchOperationUI rename(Repository repository) {
		return new BranchOperationUI(repository, MODE_RENAME);
	}

	public static BranchOperationUI delete(Repository repository) {
		return new BranchOperationUI(repository, MODE_DELETE);
	}

	public static BranchOperationUI create(Repository repository) {
		BranchOperationUI op = new BranchOperationUI(repository, MODE_CREATE);
		return op;
	}

	public static BranchOperationUI checkout(Repository repository) {
		return new BranchOperationUI(repository, MODE_CHECKOUT);
	}

	public static BranchOperationUI checkout(Repository repository,
			String target) {
		return new BranchOperationUI(repository, target);
