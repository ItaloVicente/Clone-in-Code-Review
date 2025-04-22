	/**
	 * Select and checkout a branch
	 *
	 * @param repository
	 * @param mode
	 */
	private BranchOperationUI(Repository repository, int mode) {
		this.repository = repository;
		this.mode = mode;
		this.showQuestionsBeforeCheckout = true;
