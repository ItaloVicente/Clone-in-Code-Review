	private void deleteSymbolicLinkFromProject(IProject project,
			IProgressMonitor monitor) {
		IFile symbolicLink = project.getFile(Constants.DOT_GIT);
		IWorkspaceRunnable wsr = actMonitor -> {
			symbolicLink.delete(false, actMonitor);
		};
		try {
			ResourcesPlugin.getWorkspace().run(wsr, monitor);
		} catch (CoreException e1) {
			Activator.logError(e1.getMessage(), e1);
		}

	}

