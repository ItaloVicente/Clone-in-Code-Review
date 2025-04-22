
	public static WorkspaceFileRevision forFile(Repository repository,
			IResource resource) {
		IPath path = ResourceUtil
				.getRepositoryRelativePath(resource.getLocation(), repository);
		String repoRelativePath = path == null ? null : path.toString();
		return new WorkspaceFileRevision(repository, repoRelativePath,
				resource);
	}

