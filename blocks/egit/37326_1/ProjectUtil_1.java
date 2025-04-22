	}

	public static void refreshRepositoryResources(Repository repository,
			Collection<String> relativePaths, IProgressMonitor monitor)
			throws CoreException {
		if (relativePaths.isEmpty() || relativePaths.contains("")) { //$NON-NLS-1$
			refreshResources(getProjects(repository), monitor);
			return;
		}
