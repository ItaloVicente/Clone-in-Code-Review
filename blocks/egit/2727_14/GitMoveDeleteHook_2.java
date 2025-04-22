	private void mapProject(final IProject source,
			final IProjectDescription description,
			final IProgressMonitor monitor, IPath gitDir) throws CoreException,
			TeamException {
		IProject destination = source.getWorkspace().getRoot()
				.getProject(description.getName());
		GitProjectData projectData = new GitProjectData(destination);
		RepositoryMapping repositoryMapping = new RepositoryMapping(
				destination, gitDir.toFile());
		projectData.setRepositoryMappings(Arrays
				.asList(repositoryMapping));
		projectData.store();
		GitProjectData.add(destination, projectData);
		RepositoryProvider
				.map(destination, GitProvider.class.getName());
		destination.refreshLocal(IResource.DEPTH_INFINITE,
				new SubProgressMonitor(monitor, 50));
	}

	private boolean unmapProject(final IResourceTree tree, final IProject source) {
		try {
			RepositoryProvider.unmap(source);
		} catch (TeamException e) {
			tree.failed(new Status(IStatus.ERROR, Activator
					.getPluginId(), 0,
					CoreText.MoveDeleteHook_operationError, e));
					return true; // Do not let Eclipse complete the operation
		}
		return false;
	}

