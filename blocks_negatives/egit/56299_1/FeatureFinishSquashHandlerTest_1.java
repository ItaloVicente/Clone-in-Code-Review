	private void init() throws CoreException {
		new InitOperation(repository).execute(null);
	}

	private void checkoutBranch(String branchToCheckout) throws CoreException {
		new BranchOperation(repository, branchToCheckout).execute(null);
