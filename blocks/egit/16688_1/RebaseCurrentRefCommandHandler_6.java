	@Override
	protected Object execute(RebaseOperation rebase) throws ExecutionException {
		Repository repository = rebase.getRepository();
		if (!repository.getRepositoryState().equals(RepositoryState.SAFE))
			throw new IllegalStateException(
					"Can't start rebase if repository state isn't SAFE"); //$NON-NLS-1$
		return super.execute(rebase);
