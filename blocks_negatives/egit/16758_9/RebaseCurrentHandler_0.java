	public boolean isEnabled() {
		final Repository repository = getRepository(getPage());
		if (repository == null)
			return false;
		return repository.getRepositoryState().equals(RepositoryState.SAFE);
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {

		PlotCommit commit = (PlotCommit) getSelection(getPage()).getFirstElement();
		final Repository repository = getRepository(event);
		if (repository == null)
			return null;

		String currentBranch = getCurrentBranch(repository);
		final Ref ref = getRef(commit, repository, currentBranch);

		String jobname = NLS.bind(
				UIText.RebaseCurrentRefCommand_RebasingCurrentJobName,
				currentBranch, ref.getName());
		AbstractRebaseCommandHandler rebaseCurrentRef = new AbstractRebaseCommandHandler(
				jobname, UIText.RebaseCurrentRefCommand_RebaseCanceledMessage) {
			@Override
			protected RebaseOperation createRebaseOperation(
					Repository repository2) throws ExecutionException {
				return new RebaseOperation(repository2, ref);
			}
		};
		rebaseCurrentRef.execute(repository);
		return null;
