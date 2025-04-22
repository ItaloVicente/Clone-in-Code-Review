	private boolean checkRepository() {
		if (Activator.getDefault() == null)
			return false;
		if (!repository.getDirectory().exists())
			return false;
		return true;
	}

	private void waitForWorkspaceLock() {
		try {
			ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {

				public void run(IProgressMonitor monitor) throws CoreException {
				}
			}, new NullProgressMonitor());
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
	}

