	private void cherryPick(@NonNull RevCommit commit,
			IProgressMonitor monitor) {
		monitor.subTask(UIText.FetchGerritChangePage_CherryPickTaskName);
		WorkbenchJob job = new WorkbenchJob(
				PlatformUI.getWorkbench().getDisplay(),
				UIText.FetchGerritChangePage_CherryPickTaskName) {

			@Override
			public IStatus runInUIThread(IProgressMonitor progress) {
				try {
					CherryPickUI ui = new CherryPickUI();
					ui.run(repository, commit, false);
				} catch (CoreException e) {
					return Activator.createErrorStatus(e.getLocalizedMessage(),
							e);
				} finally {
					progress.done();
				}
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
		monitor.worked(1);
	}

