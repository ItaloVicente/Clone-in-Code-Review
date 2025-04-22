		IWorkspaceRunnable wsr = new IWorkspaceRunnable() {

			@Override
			public void run(IProgressMonitor actMonitor)
			throws CoreException {

				for (IProject prj : projectsToDelete)
					prj.delete(delete, false, actMonitor);
