	public void onRefsChanged(RefsChangedEvent event) {
		if (getCommit().getRepository().getDirectory()
				.equals(event.getRepository().getDirectory())) {
			UIJob job = new UIJob("Refreshing editor") { //$NON-NLS-1$

				public IStatus runInUIThread(IProgressMonitor monitor) {
					commitPage.refresh();
					return Status.OK_STATUS;
				}
			};
			job.schedule();
		}
	}
