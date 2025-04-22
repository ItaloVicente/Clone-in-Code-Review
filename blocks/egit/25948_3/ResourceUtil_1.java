	public static IResource getResourceHandleForLocation(Repository repository,
			String repoRelativePath, int fileMode) {
		final String workDir = repository.getWorkTree().getAbsolutePath();
		final IPath path = new Path(workDir + '/' + repoRelativePath);
		final File file = path.toFile();
		if (file.exists()) {
			if (file.isDirectory())
				return ResourceUtil.getContainerForLocation(path);
			else
				return ResourceUtil.getFileForLocation(path);
		}

		if (!FileMode.TREE.equals(fileMode)
				&& !FileMode.REGULAR_FILE.equals(fileMode))
			return null;
