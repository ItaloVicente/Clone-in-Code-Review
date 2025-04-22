		}

		if (monitor.isCanceled())
			return;

		IWorkspaceRunnable wsr = new IWorkspaceRunnable() {

			public void run(IProgressMonitor actMonitor) throws CoreException {

				for (IProject prj : projectsToDelete) {
					prj.delete(false, false, actMonitor);
				}
				for (Repository repo : repository)
					removeDir(repo.getDirectory());
				scheduleRefresh();
