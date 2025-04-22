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

