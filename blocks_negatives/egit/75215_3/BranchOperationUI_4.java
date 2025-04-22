	/**
	 * Create an operation for checking out a branch without showing a question
	 * dialog about the target.
	 *
	 * @param repository
	 * @param target
	 *            a valid {@link Ref} name or commit id
	 * @return the {@link BranchOperationUI}
	 */
	public static BranchOperationUI checkoutWithoutQuestion(
			Repository repository, String target) {
		return new BranchOperationUI(repository, target, false);
	}

