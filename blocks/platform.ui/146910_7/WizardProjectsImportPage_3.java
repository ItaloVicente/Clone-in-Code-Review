	private void buildIfRequested(final IProject project, IProgressMonitor monitor) throws CoreException {
		if (rebuildProjectsAfterImport) {
			project.build(IncrementalProjectBuilder.CLEAN_BUILD, monitor);
			IWorkspace workspace = project.getWorkspace();
			boolean isAutoBuilding = workspace.isAutoBuilding();
			if (!isAutoBuilding) {
				project.build(IncrementalProjectBuilder.FULL_BUILD, monitor);
			}
		}
	}

