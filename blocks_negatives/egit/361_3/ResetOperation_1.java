	private void refreshProjects() {
		final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		final File parentFile = repository.getWorkDir();
		for (IProject p : projects) {
			final File file = p.getLocation().toFile();
			if (file.getAbsolutePath().startsWith(parentFile.getAbsolutePath())) {
				try {
					if (GitTraceLocation.CORE.isActive())
						GitTraceLocation.getTrace().trace(
								GitTraceLocation.CORE.getLocation(),
					p.refreshLocal(IResource.DEPTH_INFINITE, null);
				} catch (CoreException e) {
					if (GitTraceLocation.CORE.isActive())
						GitTraceLocation.getTrace().trace(GitTraceLocation.CORE.getLocation(), e.getMessage(), e);
				}
			}
		}
	}

