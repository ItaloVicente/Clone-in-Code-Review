	public static BranchOperationUI createWithRef(Repository repository, String baseRef) {
		BranchOperationUI op = new BranchOperationUI(repository, MODE_CREATE);
		op.base = baseRef;
		return op;
	}

