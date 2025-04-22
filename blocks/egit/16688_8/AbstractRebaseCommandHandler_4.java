	public void execute(Repository repository) throws ExecutionException {
		final RebaseOperation rebase = createRebaseOperation(repository);
		execute(rebase);
	}

	private void handleBeginError(final Repository repository, IStatus result) {
		if (!repository.getRepositoryState().equals(RepositoryState.SAFE)) {
			Throwable t = result.getException();
			try {
				new RebaseOperation(repository, Operation.ABORT).execute(null);
				Activator.showError(t.getMessage(), t);
			} catch (CoreException e1) {
				IStatus childStatus = Activator.createErrorStatus(
						e1.getMessage(), e1);
				IStatus mStatus = new MultiStatus(Activator.getPluginId(),
						IStatus.ERROR, new IStatus[] { childStatus },
						t.getMessage(), t);
				CoreException mStatusException = new CoreException(mStatus);
				Activator.showError(mStatusException.getMessage(),
						mStatusException);
			}
		}
	}

	protected abstract RebaseOperation createRebaseOperation(
			Repository repository) throws ExecutionException;

