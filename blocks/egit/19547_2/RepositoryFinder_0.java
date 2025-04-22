	private void findInDirectoryAndParents(IContainer container,
			File path) {
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		builder.addCeilingDirectories(ceilingDirectories);
		builder.findGitDir(path);
		File gitDir = builder.getGitDir();
		if (gitDir != null)
			register(container, gitDir);
	}

	private void findInDirectory(final IContainer container,
			final File path) {
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		builder.addCeilingDirectory(path);
		builder.findGitDir(path);
		File gitDir = builder.getGitDir();
		if (gitDir != null)
			register(container, gitDir);
