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
			IProject[] project = ProjectUtil.getProjectsContaining(repository,
					Collections.singleton(repoRelativePath.toString()));
			if (project != null && project.length > 0) {
				IPath projectRelativePath = gitPath
						.makeRelativeTo(project[0].getLocation());
				IPath rootRelativePath = project[0].getFullPath()
						.append(projectRelativePath);
				return root.getFile(rootRelativePath);
			}
		} catch (CoreException e) {
		}
		return root.getFile(gitPath);
	}

