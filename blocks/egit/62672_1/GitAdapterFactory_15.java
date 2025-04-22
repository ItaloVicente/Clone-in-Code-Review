	@Nullable
	private IResource getWorkspaceResourceFromGitPath(IPath gitPath) {
		Repository repository = Activator.getDefault().getRepositoryCache()
				.getRepository(gitPath);
		if (repository == null || repository.isBare()) {
			return null;
		}
		try {
			IPath repoRelativePath = gitPath.makeRelativeTo(
					new Path(repository.getWorkTree().getAbsolutePath()));
			IProject[] projects = ProjectUtil.getProjectsContaining(repository,
					Collections.singleton(repoRelativePath.toString()));
			if (projects.length > 0) {
				IPath projectRelativePath = gitPath
						.makeRelativeTo(projects[0].getLocation());
				return projects[0].getFile(projectRelativePath);
			}
		} catch (CoreException e) {
		}
		return root.getFile(gitPath);
	}

	@Nullable
	private static Repository getRepository(IURIEditorInput uriInput) {
		File file = getFile(uriInput);
		if (file == null) {
			return null;
		}
		Path path = new Path(file.getAbsolutePath());
		RepositoryMapping mapping = RepositoryMapping.getMapping(path);
		if (mapping != null) {
			return mapping.getRepository();
		}
		Repository repository = org.eclipse.egit.core.Activator.getDefault()
				.getRepositoryCache().getRepository(path);
		return repository;
	}

	@Nullable
	private static File getFile(IURIEditorInput uriInput) {
		URI uri = uriInput.getURI();
		if (uri == null) {
			return null;
		}
		try {
			IFileStore store = EFS.getStore(uri);
			if (store != null) {
				return store.toLocalFile(EFS.NONE, new NullProgressMonitor());
			}
		} catch (CoreException ce) {
		}
		return null;
	}

