	public static IResource getResourceHandleForLocation(Repository repository,
			String repoRelativePath, boolean isFolder) {
		final String workDir = repository.getWorkTree().getAbsolutePath();
		final IPath path = new Path(workDir + '/' + repoRelativePath);
		final File file = path.toFile();
		if (file.exists()) {
			if (isFolder) {
				return getContainerForLocation(path);
			}
			return getFileForLocation(path);
		}
