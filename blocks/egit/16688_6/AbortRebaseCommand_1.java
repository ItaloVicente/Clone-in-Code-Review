
	@Override
	public RebaseOperation createRebaseOperation(ExecutionEvent event)
			throws ExecutionException {
		return new RebaseOperation(getRepository(event), Operation.ABORT);
	}
