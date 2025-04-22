		return;
	}

	protected Repository getRepository(ExecutionEvent event) {
		return AbstractSharedCommandHandler.extractRepository(event);
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		final RebaseOperation rebase = createRebaseOperation(event);
		execute(rebase);
