	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Repository repository = getRepository(event);
		if (repository == null)
			return null;
		final RebaseOperation rebase = new RebaseOperation(repository,
				this.operation);
