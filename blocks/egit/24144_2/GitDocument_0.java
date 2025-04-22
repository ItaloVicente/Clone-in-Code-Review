	public void onRefsChanged(final RefsChangedEvent event) {
		cancelReloadJob();

		reloadJob = new Job(UIText.GitDocument_ReloadJobName) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
