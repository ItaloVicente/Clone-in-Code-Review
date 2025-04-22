	public static BranchOperationUI rename(Repository repository) {
		return new BranchOperationUI(repository, MODE_RENAME);
	}

	public static BranchOperationUI delete(Repository repository) {
		return new BranchOperationUI(repository, MODE_DELETE);
