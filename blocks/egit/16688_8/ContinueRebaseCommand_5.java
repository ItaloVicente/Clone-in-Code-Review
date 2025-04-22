
	@Override
	protected RebaseOperation createRebaseOperation(Repository repository)
			throws ExecutionException {
		return new RebaseOperation(repository, Operation.CONTINUE);
	}
