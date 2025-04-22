	private void buildIfRequested(final IProject project, IProgressMonitor monitor) throws CoreException {
		if (!closeProjectsAfterImport && rebuildProjectsAfterImport) {
			project.build(IncrementalProjectBuilder.CLEAN_BUILD, monitor);
			project.build(IncrementalProjectBuilder.FULL_BUILD, monitor);
		}
	}

