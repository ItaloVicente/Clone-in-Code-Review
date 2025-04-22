	private final int mode;

	/**
	 * Create an operation for manipulating branches
	 *
	 * @param repository
	 * @return the {@link BranchOperationUI}
	 */
	public static BranchOperationUI rename(Repository repository) {
		return new BranchOperationUI(repository, MODE_RENAME);
	}

	/**
	 * Create an operation for manipulating branches
	 *
	 * @param repository
	 * @return the {@link BranchOperationUI}
	 */
	public static BranchOperationUI delete(Repository repository) {
		return new BranchOperationUI(repository, MODE_DELETE);
	}

	/**
	 * Create an operation for creating a local branch
	 *
	 * @param repository
	 * @return the {@link BranchOperationUI}
	 */
	public static BranchOperationUI create(Repository repository) {
		BranchOperationUI op = new BranchOperationUI(repository, MODE_CREATE);
		return op;
	}

	/**
	 * Create an operation for creating a local branch with a given base reference
	 *
	 * @param repository
	 * @param baseRef
	 * @return the {@link BranchOperationUI}
	 */
	public static BranchOperationUI createWithRef(Repository repository, String baseRef) {
		BranchOperationUI op = new BranchOperationUI(repository, MODE_CREATE);
		op.base = baseRef;
		return op;
	}

	/**
	 * Create an operation for checking out a local branch
	 *
	 * @param repository
	 * @return the {@link BranchOperationUI}
	 */
	public static BranchOperationUI checkout(Repository repository) {
		return new BranchOperationUI(repository, MODE_CHECKOUT);
	}

