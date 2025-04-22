	private void refreshRepositoriesView() {
		UIJob job = new UIJob(
				UIText.CleanRepositoryPage_RefreshingRepositories) {

			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				RepositoriesView view = (RepositoriesView) PlatformUI
						.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().findView(RepositoriesView.VIEW_ID);
				if (view != null) {
					view.refresh();
				}
				return Status.OK_STATUS;
			}
		};
		job.schedule();
	}

