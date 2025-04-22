	public Object execute(ExecutionEvent event) throws ExecutionException {
		final RebaseOperation rebase = getRebaseOperation(event);
		if (rebase == null)
			return null;
		return execute(rebase);
	}

	public abstract RebaseOperation getRebaseOperation(ExecutionEvent event)
			throws ExecutionException;

	public abstract String getJobName(RebaseOperation operation)
			throws ExecutionException;


