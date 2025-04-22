	protected Path getFullPath(String repositoryFilename) {
		Path dotGitPath = db.getDirectory().toPath();
		Path repositoryRoot = dotGitPath.getParent();
		Path repositoryFilePath = repositoryRoot.resolve(repositoryFilename);
		return repositoryFilePath;
	}

