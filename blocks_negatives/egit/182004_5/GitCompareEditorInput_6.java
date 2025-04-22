	@Override
	protected void contentsCreated() {
		super.contentsCreated();
		getNavigator().selectChange(true);
	}

	@Override
	protected void handleDispose() {
		super.handleDispose();
	}

	private IDiffContainer buildDiffContainer(RevCommit baseCommit,
			RevCommit compareCommit, IProgressMonitor monitor)
