		return;
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		final RebaseOperation rebase = createRebaseOperation(event);
		execute(rebase);
