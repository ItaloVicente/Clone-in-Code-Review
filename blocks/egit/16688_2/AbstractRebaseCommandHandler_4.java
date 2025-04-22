	public Object execute(ExecutionEvent event) throws ExecutionException {
		final RebaseOperation rebase = createRebaseOperation(event);
		if (rebase == null)
			return null;
		return execute(rebase);
	}

	protected abstract RebaseOperation createRebaseOperation(
			ExecutionEvent event) throws ExecutionException;

