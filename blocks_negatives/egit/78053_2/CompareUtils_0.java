			Repository repository) {
		RepositoryMapping mapping = RepositoryMapping.getMapping(location);
		final String gitPath;
		if (mapping != null)
			gitPath = mapping.getRepoRelativePath(location);
		else {
			IPath repoRoot = new Path(repository.getWorkTree().getPath());
			gitPath = location.makeRelativeTo(repoRoot).toString();
		}
