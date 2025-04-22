	private void findInDirectoryAndParents(IContainer container, File startPath) {
		File path = startPath;
		while (path != null && !ceilingDirectories.contains(path)) {
			findInDirectory(container, path);
			path = path.getParentFile();
		}
	}

	private void findInDirectory(final IContainer container,
			final File path) {
		if (GitTraceLocation.CORE.isActive())
			GitTraceLocation.getTrace().trace(
					GitTraceLocation.CORE.getLocation(),
					"Looking at candidate dir: " //$NON-NLS-1$
							+ path);

		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		File parent = path.getParentFile();
		if (parent != null)
			builder.addCeilingDirectory(parent);
		builder.findGitDir(path);
		File gitDir = builder.getGitDir();
		if (gitDir != null)
			register(container, gitDir);
