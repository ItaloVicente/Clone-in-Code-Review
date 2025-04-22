	@Override
	public void done(IJobChangeEvent event) {
		if (event.getResult().getSeverity() == IStatus.CANCEL) {
			return;
		}
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				String repoName = Activator.getDefault().getRepositoryUtil()
						.getRepositoryName(repository);
				Dialog dialog = new FetchResultDialog(PlatformUI.getWorkbench()
						.getDisplay().getActiveShell(), repository,
						operationResult, repoName + " - " + remote.getName()); //$NON-NLS-1$
				dialog.open();
			}
		});
